TOKEN_TYPES = [
    "NUM", "STRING",
    "TRUE", "FALSE",
    "PRINT", "STR", "DEF", "DEFN", "LET", "LIST", "HEAD", "TAIL", "NTH", "IF", "DO",
    "ID",
    "AOP", "COP",
    "LPAREN", "RPAREN",
    "EOF"
]

class Token:
    def __init__(self, type_, value=None):
        self.type = type_
        self.value = value

    def __repr__(self):
        if self.value:
            return f"{self.type}({self.value})"
        return f"{self.type}"
    
class Lexer:
    def __init__(self,text):
        if (type(text) != list):
            self.text = list(text)
        else:
            self.text = text
        self.pos = 0

    def next_token(self) -> Token: 
        while (len(self.text) > self.pos):
            match(self.peek()):
                case '(':   
                    self.consume()
                    return Token("LPAREN")
                case ')':   
                    self.consume()
                    return Token("RPAREN")
                case ' ':   
                    self.ws()
                case '\t':   
                    self.ws()
                case '\n':   
                    self.ws()
                case '+':   
                    self.consume()
                    return Token("AOP","+")
                case '-':   
                    self.consume()
                    return Token("AOP","-")
                case '*':   
                    self.consume()
                    return Token("AOP","*")
                case '/':   
                    self.consume()
                    return Token("AOP","/")
                case '=':   
                    self.consume()
                    return Token("COP","=")
                case '<':   
                    self.consume()
                    return Token("COP","<")
                case '>':   
                    self.consume()
                    return Token("COP",">")
                case ';':   
                    if(self.match(";")):
                        self.consume()
                        self.go_to_next_line()
                case '"':   
                    return self.stringToken()
                case _: 
                    return self.complexToken()
                    

    def complexToken(self) -> Token:
        word = self.readString()
        match(word):
            case "if": return Token("IF", word)
            case "true": return Token("TRUE", word)
            case "false": return Token("FALSE", word)
            case "print": return Token("PRINT", word)
            case "str": return Token("STR", word)
            case "def": return Token("DEF", word)
            case "defn": return Token("DEFN", word)
            case "let": return Token("LET", word)
            case "list": return Token("LIST", word)
            case "head": return Token("HEAD", word)
            case "tail": return Token("TAIL", word)
            case "nth": return Token("NTH", word)
            case "do": return Token("DO", word)
            case _: 
                numbers = ["0","1","2","3","4","5","6","7","8","9"]
                if(list(word)[0] in numbers): return Token("NUM", word)
                else: return Token("ID", word)

    def go_to_next_line(self):
        chars = ["\n"]
        while(self.peek() not in chars):
            self.consume()
            self.isPosValid('expected char not found: \\n')
        self.consume()

    def readString(self):
        chars = [" ", "\t", "\n"]
        word = ""
        while((self.peek() not in chars) and (self.pos >= len(self.text))):
            word += self.peek()
            self.consume()

        self.ws()
        return word

    def stringToken(self)-> str:
        chars = ['"']
        string = ""
        self.consume()
        self.isPosValid('"')
        while(self.peek() not in chars):
            string += self.peek()
            self.consume()
            self.isPosValid('expected char not found: "')
        self.consume()
        return Token("STRING", string)

    def ws(self):
        chars = [" ", "\t", "\n"]
        while(self.peek() in chars):
            self.consume()
            self.isPosValid("End Of file")

    def isPosValid(self, text):
        if(self.pos >= len(self.text)):
            raise Exception(text)

    def peek(self):
        return self.text[self.pos]
    
    def match(self,c):
        if (c == self.text[self.pos + 1]):
            self.consume()
            return True
        return
    
    def rollback(self):
        self.text[self.pos - 1]
    
    def consume(self):
        self.pos += 1