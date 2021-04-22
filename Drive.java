//Liam Robb, lr003
public enum Drive {
    A(0), B(1), C(2); // could make this the T/F bit maybe with killed as 3rd
    //a,b,c
    private final int id;
    Drive(int id) {this.id = id;}
    public int getValue() {return id;}

}
