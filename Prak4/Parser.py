from Lexer import Lexer

class Node:
    def __init__(self, type, val = None):
        self.type = type
        self.children = []
        self.val = val
        
    def addChild(self, name, val=None):
        n = Node(name, val)
        self.children.append(n)
        return n

class Parser:
    def __init__(self, text):
        self.lexer = Lexer(text)
        self.tokens = [self.lexer.next_token()]
        self.pos = 0
        self.tree = Node("START")

    def matchType(self, checkType):
        if self.current().type == checkType:
            return True
        else:
            return False
        
    def lookahead(self):
        self.consume()
        pos -= 1
        return self.tokens[pos+1]
    
    def consume(self):
        self.pos += 1
        if self.pos == len(self.tokens):
            self.tokens.append(self.lexer.next_token())

    def current(self):
        return self.tokens[self.pos]

    # Parser Rules
    def start(self):
        while True:
            match(self.current().type):
                case "EOF":
                    self.tree.addChild("EOF", "EOF")
                    return self.tree
                case _:
                    self.expression()

    def expression(self, node = None):
        if node == None:
            node = self.tree
        node = node.addChild("EXPR")

        match(self.current().type):
            case "LPAREN":
                self.subexpression(node)
            case "ID":
                self.id(node)
            case _:
                self.literal(node)
    
    def subexpression(self, node):
        self.consume()
        match(self.current().type):
            case "PRINT": 
                self.print1(node) 
            case "STR": 
                self.strkw(node) 
            case "DEF": 
                self.def1(node) 
            case "DEFN": 
                self.defn(node)
            case "LET": 
                self.let(node) 
            case "LIST": 
                self.list1(node) 
            case "HEAD": 
                self.head(node) 
            case "TAIL": 
                self.tail(node) 
            case "NTH": 
                self.nth(node) 
            case "IF":
                self.if1(node) 
            case  "DO":
                self.do(node)
            case _:
                self.call(node)

    def literal(self, node):
        node = node.addChild("LITERAL")

        match(self.current().type):
            case "NUM":
                self.num(node)
            case "STRING":
                self.string(node)
            case "TRUE":
                self.true(node)
            case "FALSE":
                self.false(node)
            case _:
                raise Exception("Unexpected Token")

    def call(self, node):
        node = node.addChild("CALL")

        match(self.current().type):
            case "AOP":
                node = node.addChild("OPERATOR")
                self.aop(node)
                while True:
                    self.expression(node)
                    if self.matchType("RPAREN"):
                        break
            case "COP":
                node = node.addChild("OPERATOR")
                self.cop(node)
                while True:
                    self.expression(node)
                    if self.matchType("RPAREN"):
                        break
            case "ID":
                self.id(node)
                while not self.matchType("RPAREN"):
                    self.expression(node)
            case _:
                raise Exception("Unexpected Token")
            
        if self.matchType("RPAREN"):
                self.consume()
        else:
            raise Exception("Expected RPAREN Token")

    # Lexer Rules

    def num(self, node):
        if not self.matchType("NUM"):
            raise Exception("Called Method with wrong Type")

        node.addChild("NUM", self.current().value)
        self.consume()

    def string(self, node):
        if not self.matchType("STRING"):
            raise Exception("Called Method with wrong Type")

        node.addChild("STRING", self.current().value)
        self.consume()

    def true(self, node):
        if not self.matchType("TRUE"):
            raise Exception("Called Method with wrong Type")

        node.addChild("TRUE")
        self.consume()

    def false(self, node):
        if not self.matchType("FALSE"):
            raise Exception("Called Method with wrong Type")

        node.addChild("FALSE")
        self.consume()

    def print1(self, node):
        if not self.matchType("PRINT"):
            raise Exception("Called Method with wrong Type")

        node = node.addChild("PRINT")
        self.consume()

        self.literal()

        if self.matchType("RPAREN"):
            self.consume()
        else:
            raise Exception("Expected RPAREN Token")

    def strkw(self, node):
        if not self.matchType("STR"):
            raise Exception("Called Method with wrong Type")

        node = node.addChild("STR")
        self.consume()

        while True:
            match(self.current().type):
                case "ID":
                    self.id(node)
                case _:
                    self.literal(node)
            if self.matchType("RPAREN"):
                break

        if self.matchType("RPAREN"):
            self.consume()
        else:
            raise Exception("Expected RPAREN Token")

    def def1(self, node):
        if not self.matchType("DEF"):
            raise Exception("Called Method with wrong Type")

        node = node.addChild("DEF")
        self.consume()

        self.id(node)
        self.expression(node)

        if self.matchType("RPAREN"):
            self.consume()
        else:
            raise Exception("Expected RPAREN Token")


    def defn(self, node):
        if not self.matchType("DEFN"):
            raise Exception("Called Method with wrong Type")

        node = node.addChild("DEFN")
        self.consume()
        self.id(node)

        if self.matchType("LPAREN"):
            self.consume()
        else:
            raise Exception("Expected LPAREN Token")
        
        while self.matchType("ID"):
            id(node)

        if self.matchType("RPAREN"):
            self.consume()
        else:
            raise Exception("Expected RPAREN Token")
        
        self.expression(node)

        if self.matchType("RPAREN"):
            self.consume()
        else:
            raise Exception("Expected RPAREN Token")

    def let(self, node):
        if not self.matchType("LET"):
            raise Exception("Called Method with wrong Type")

        node = node.addChild("LET")
        self.consume()

        if self.matchType("LPAREN"):
            self.consume()
        else:
            raise Exception("Expected LPAREN Token")
        
        while True:
            self.id(node)
            self.expression(node)
            if self.matchType("RPAREN"):
                break

        if self.matchType("RPAREN"):
            self.consume()
        else:
            raise Exception("Expected RPAREN Token")
        
        self.expression(node)

        if self.matchType("RPAREN"):
            self.consume()
        else:
            raise Exception("Expected RPAREN Token")

    def list1(self, node):
        if not self.matchType("LIST"):
            raise Exception("Called Method with wrong Type")

        node = node.addChild("LIST")
        self.consume()

        while True:
            self.expression(node)
            if self.matchType("RPAREN"):
                break

        if self.matchType("RPAREN"):
            self.consume()
        else:
            raise Exception("Expected RPAREN Token")

    def head(self, node):
        if not self.matchType("HEAD"):
            raise Exception("Called Method with wrong Type")

        node = node.addChild("HEAD")
        self.consume()
        self.list1(node)
        
        if self.matchType("RPAREN"):
            self.consume()
        else:
            raise Exception("Expected RPAREN Token")

    def tail(self, node):
        if not self.matchType("TAIL"):
            raise Exception("Called Method with wrong Type")

        node = node.addChild("TAIL")
        self.consume()
        self.list1(node)
        
        if self.matchType("RPAREN"):
            self.consume()
        else:
            raise Exception("Expected RPAREN Token")

    def nth(self, node):
        if not self.matchType("NTH"):
            raise Exception("Called Method with wrong Type")

        node = node.addChild("NTH")
        self.consume()
        self.list1(node)
        match(self.current().type):
            case "ID":
                self.id(node)
            case "NUM":
                self.num(node)
            case _:
                raise Exception("Expected Num or ID")
        
        if self.matchType("RPAREN"):
            self.consume()
        else:
            raise Exception("Expected RPAREN Token")

    def if1(self, node):
        if not self.matchType("IF"):
            raise Exception("Called Method with wrong Type")

        node = node.addChild("IF")
        self.consume()
        match(self.current().type):
            case "TRUE":
                self.true()
            case "FALSE":
                self.false()
            case "LPAREN":
                self.consume()
                if self.matchType("COP"):
                    self.cop()
                else:
                    raise Exception("Expected COP")
                while True:
                    self.expression(node)
                    if self.matchType("RPAREN"):
                        break
            case _:
                raise Exception("Expected TRUE, FALSE or LPAREN")

        self.expression(node)
        if not self.lookahead().type == "RPAREN":
            self.expression(node)
        if self.matchType("RPAREN"):
            self.consume()
        else:
            raise Exception("Expected RPAREN Token")

    def do(self, node):
        if not self.matchType("DO"):
            raise Exception("Called Method with wrong Type")

        node = node.addChild("DO")
        self.consume()

        while True:
            self.expression(node)
            if self.matchType("RPAREN"):
                break

        if self.matchType("RPAREN"):
            self.consume()
        else:
            raise Exception("Expected RPAREN Token")

    def id(self, node):
        if not self.matchType("ID"):
            raise Exception("Called Method with wrong Type")

        node.addChild("ID", self.current().value)
        self.consume()

    def aop(self, node):
        if not self.matchType("AOP"):
            raise Exception("Called Method with wrong Type")

        node.addChild("AOP", self.current().value)
        self.consume()

    def cop(self, node):
        if not self.matchType("COP"):
            raise Exception("Called Method with wrong Type")

        node.addChild("COP", self.current().value)
        self.consume()

def printTree(node: Node):
        if node.children == []:
            return node.val
        string = str(node.type) + "("
        i = True
        for c in node.children:
            if i:
                string += printTree(c)
                i = False
            else:
                string += ", " + str(printTree(c))

        string += ")"
        return string

if __name__ == "__main__":
    data = ""
    with open('Prak4/test.txt', 'r') as file:
        data = file.read()
    parser = Parser(data)
    a = parser.start()
    print(printTree(a))
