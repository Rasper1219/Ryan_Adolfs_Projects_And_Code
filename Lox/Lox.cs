using System;
using System.IO;
using System.Text;
using System.Collections.Generic;





namespace CraftingInterpreters.Lox
{
    class Lox
    {   
        private static readonly Interpreter Interpreters = new Interpreter();

        static bool HadError = false;
        static bool HadRuntimeError = false;
        static void Main(string[] args)
        {
            if (args.Length > 1)
            {
                Console.WriteLine("Usage: clox [script]");
                Environment.Exit(64);
            }
            else if (args.Length == 1)
            {
                RunFile(args[0]);
            }
            else
            {
                RunPrompt();
            }
        }

        private static void RunFile(string path)
        {
            try
            {
                byte[] bytes = File.ReadAllBytes(path);
                Run(Encoding.Default.GetString(bytes));

                if (HadError)
                {
                    Environment.Exit(65);
                }
                if (HadRuntimeError) Environment.Exit(70);

            }
            catch (IOException e)
            {
                Console.WriteLine($"Error reading file: {e.Message}");
                Environment.Exit(74);
            }
        }

        private static void RunPrompt()
        {
            for (;;)
            {
                Console.Write("> ");
                string line = Console.ReadLine();
                if (line == null) break;
                Run(line);
                HadError = false;
            }
        }
        private static void Run(string source)
        {
            Scanner scanner = new Scanner(source);
            List<Token> tokens = scanner.ScanTokens();

            Parser parser = new Parser(tokens);
            List<Stmt> statements = parser.Parse();


            
            if (HadError) return;
            
            Interpreters.Interpret(statements);


        }

        public static void Error(int line, string message)
        {
            Report(line, "", message);
        }

        private static void Report(int line, string where, string message)
        {
            Console.Error.WriteLine($"[line {line}] Error{where}: {message}");
            HadError = true;
        }

        public static void Error(Token token, string message)
        {
            if (token.Type == TokenType.EOF)
            {
                Report(token.Line, " at end", message);
            }
            else
            {
                Report(token.Line, " at '" + token.Lexeme + "'", message);
            }
        }

        public static void RuntimeError(RuntimeError error)
        {
            Console.Error.WriteLine($"{error.Message}\n[line {error.Token.Line}]");
            HadRuntimeError = true;
        }







    }
}