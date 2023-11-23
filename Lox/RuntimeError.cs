using System;
using System.IO;
using System.Text;
using System.Collections.Generic;


namespace CraftingInterpreters.Lox
{
    public class RuntimeError : Exception
    {
        public Token Token { get; }

        public RuntimeError(Token token, string message) : base(message)
        {
            Token = token;
        }
    }
}