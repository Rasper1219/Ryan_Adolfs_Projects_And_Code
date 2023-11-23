using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace CraftingInterpreters.Tool
{
    
    public class GenerateAst
    {
        
        public static void Main2(string[] args)
        {
            
            if (args.Length != 1)
            {
                Console.Error.WriteLine("Usage: generate_ast <output directory>");
                Environment.Exit(64); // Exit with code 64 (command line usage error).
            }

            string outputDir = args[0];
            DefineAst(outputDir, "Expr", new List<string>
            {
                // Define Expressions
                "Binary : Expr Left, Token Operator, Expr Right",
                "Grouping : Expr Expression",
                "Literal : object Value",
                "Unary : Token Operator, Expr Right"
            });

            DefineAst(outputDir, "Stmt", new List<string>
            {
                "Expression : Expr Expression",
                "Print      : Expr Expression"
            });

        } 

        
        private static void DefineAst(string outputDir, string baseName, List<string> types)
        {
            // Create the path for the output file.
            string path = Path.Combine(outputDir, baseName + ".cs");

            
            using (StreamWriter writer = new StreamWriter(path, false, Encoding.UTF8))
            {
                writer.WriteLine("namespace CraftingInterpreters.Lox");
                writer.WriteLine("{");
                writer.WriteLine("    using System.Collections.Generic;");
                writer.WriteLine();
                writer.WriteLine($"    public abstract class {baseName}");
                writer.WriteLine("    {");

                
                DefineVisitor(writer, baseName, types);

                foreach (string type in types)
                {
                    string className = type.Split(':')[0].Trim();
                    string fields = type.Split(':')[1].Trim();
                    DefineType(writer, baseName, className, fields);
                }

                writer.WriteLine();
                writer.WriteLine("        public abstract T Accept<T>(Visitor<T> visitor);");
                writer.WriteLine("    }");
                writer.WriteLine("}");
            }
        }

        private static void DefineVisitor(StreamWriter writer, string baseName, List<string> types)
        {
            writer.WriteLine("        public interface Visitor<T>");
            writer.WriteLine("        {");

            foreach (string type in types)
            {
                string typeName = type.Split(':')[0].Trim();
                writer.WriteLine($"            T Visit{typeName}{baseName}({typeName} {baseName.ToLower()});");
            }

            writer.WriteLine("        }");
        }

        private static void DefineType(StreamWriter writer, string baseName, string className, string fieldList)
        {
            writer.WriteLine($"        public class {className} : {baseName}");
            writer.WriteLine("        {");

            writer.WriteLine($"            public {className}({fieldList})");
            writer.WriteLine("            {");

            string[] fields = fieldList.Split(", ");
            foreach (string field in fields)
            {
                string name = field.Split(' ')[1];
                writer.WriteLine($"                this.{name} = {name};");
            }

            writer.WriteLine("            }");

            writer.WriteLine();
            writer.WriteLine("            public override T Accept<T>(Visitor<T> visitor)");
            writer.WriteLine("            {");
            writer.WriteLine($"                return visitor.Visit{className}{baseName}(this);");
            writer.WriteLine("            }");

            writer.WriteLine();
            foreach (string field in fields)
            {
                writer.WriteLine($"            public readonly {field};");
            }

            writer.WriteLine("        }");
        }
    }
}