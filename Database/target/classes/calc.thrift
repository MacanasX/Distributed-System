namespace java server

struct Operands {
  1: i32 operand_a,
  2: i32 operand_b
}

struct Result {
  1: i32 result
}

service Calc {
  i32 addTwo(1: i32 num1, 2: i32 num2),
  Result addOne(1: Operands operands)
}

service CRUD {

  void insert (1:string a, 2:string b ,3:string c ,4:string d ,5:string e ,6:string f ,7:string g),
  string select (1:string a, 2:string b ,3:string c ,4:string d ,5:string e ,6:string f ,7:string g),
  void update (1:string a, 2:string b ,3:string c ,4:string d ,5:string e ,6:string f ,7:string g),
  void remove (1:string a ,2:string b ,3:string c ,4:string d ,5:string e ,6:string f ,7:string g)

}