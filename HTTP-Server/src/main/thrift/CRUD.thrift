namespace java databaseclient

service CRUD {

  void insert (1:string a, 2:string b ,3:string c ,4:string d ,5:string e ,6:string f ,7:string g),
  string select (1:string a, 2:string b ,3:string c ,4:string d ,5:string e ,6:string f ,7:string g),
  void update (1:string a, 2:string b ,3:string c ,4:string d ,5:string e ,6:string f ,7:string g),
  void remove (1:string a ,2:string b ,3:string c ,4:string d ,5:string e ,6:string f ,7:string g)

}