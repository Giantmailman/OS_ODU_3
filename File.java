//Liam Robb, lr003
public class File {
    private String name;
    private String data;
    private int seq;
    private Drive d;


    public String getName() {return this.name;}
    public String getData() {return this.data;}
    public int getSeq() {return this.seq;}
    public Drive getDrive() {return this.d;}
    public void setDrive(Drive drive) {this.d = drive;}

    public File(){ // default
        name = "File";
        data = "No data on disk.";
        seq = 0;
        d = Drive.A;
        //status = ProcessStatus.C; // wait what are we doing here
    }


}
