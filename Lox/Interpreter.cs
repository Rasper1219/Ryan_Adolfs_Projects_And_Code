using System;
using System.IO;
using System.Text;
using System.Collections.Generic;

namespace CraftingInterpreters.Lox
{
        class Interpreter : Expr.Visitor<object>, Stmt.Visitor<Void>
        {
        public object VisitLiteralExpr(Expr.Literal expr)
        {
            return expr.Value;
        }

        public object VisitUnaryExpr(Expr.Unary expr)
        {
            object right = Evaluate(expr.Right);

            switch (expr.Operator.Type)
            {
                case TokenType.BANG:
                    return !IsTruthy(right);
                case TokenType.MINUS:
                    CheckNumberOperand(expr.Operator, right);
                    return -(double)right;
            }

            // Unreachable.
            return null;
        }

        private void CheckNumberOperand(Token @operator, object operand)
        {
            if (operand is double)
                return;

            throw new RuntimeError(@operator, "Operand must be a number.");
        }

        private void CheckNumberOperands(Token @operator, object left, object right)
        {
            if (left is double && right is double)
                return;

            throw new RuntimeError(@operator, "Operands must be numbers.");
        }



        private bool IsTruthy(object obj)
        {
            if (obj == null) return false;
            if (obj is bool booleanValue) return booleanValue;
            return true;
        }

        private bool IsEqual(object a, object b)
        {
            if (a == null && b == null)
                return true;
            if (a == null)
                return false;

            return a.Equals(b);
        }

        private string Stringify(object obj)
        {
            if (obj == null)
                return "nil";

            if (obj is double)
            {
                string text = obj.ToString();
                if (text.EndsWith(".0"))
                {
                    text = text.Substring(0, text.Length - 2);
                }
                return text;
            }

            return obj.ToString();
        }


        public object VisitGroupingExpr(Expr.Grouping expr)
        {
            return Evaluate(expr.Expression);
        }

        private object Evaluate(Expr expr)
        {
            return expr.Accept(this);
        }

        private void Execute(Stmt stmt)
        {
            stmt.Accept(this);
        }


        public void VisitExpressionStmt(Stmt.Expression stmt)
        {
            Evaluate(stmt.Expression);
            return null;
        }

        public void VisitPrintStmt(Stmt.Print stmt)
        {
            object value = Evaluate(stmt.Expression);
            Console.WriteLine(Stringify(value));
            return null;
        }



        public object VisitBinaryExpr(Expr.Binary expr)
        {
            object left = Evaluate(expr.Left);
            object right = Evaluate(expr.Right);

            switch (expr.Operator.Type)
            {
                case TokenType.GREATER:
                    CheckNumberOperands(expr.Operator, left, right);
                    return (double)left > (double)right;
                case TokenType.GREATER_EQUAL:
                    CheckNumberOperands(expr.Operator, left, right);
                    return (double)left >= (double)right;
                case TokenType.LESS:
                    CheckNumberOperands(expr.Operator, left, right);
                    return (double)left < (double)right;
                case TokenType.LESS_EQUAL:
                    CheckNumberOperands(expr.Operator, left, right);
                    return (double)left <= (double)right;
                case TokenType.MINUS:
                    CheckNumberOperands(expr.Operator, left, right);
                    return (double)left - (double)right;
                case TokenType.PLUS:
                    if (left is double leftDouble && right is double rightDouble)
                    {
                        return leftDouble + rightDouble;
                    }

                    if (left is string leftString && right is string rightString)
                    {
                        return leftString + rightString;
                    }
                    throw new RuntimeError(expr.Operator, "Operands must be two numbers or two strings.");
                case TokenType.SLASH:
                    CheckNumberOperands(expr.Operator, left, right);
                    return (double)left / (double)right;
                case TokenType.STAR:
                    CheckNumberOperands(expr.Operator, left, right);
                    return (double)left * (double)right;
                case TokenType.BANG_EQUAL:
                    return !IsEqual(left, right);
                case TokenType.EQUAL_EQUAL:
                    return IsEqual(left, right);
            }

            // Unreachable.
            return null;
        }

        
            public void Interpret(List<Stmt> statements)
            {
                try
                {
                    foreach (Stmt statement in statements)
                    {
                        Execute(statement);
                    }
                }
                catch (RuntimeError error)
                {
                    Lox.RuntimeError(error);
                }
            }


        
    }

}