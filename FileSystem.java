//Liam robb, lr003
import java.util.Objects;
import java.util.Comparator;
import java.util.ArrayList;

public class FileSystem implements Comparable{

    private final int pid;
    private final int priority;
    private Drive drive; // this can be the "drive" we are in A,B or C
    // things for the FS: Array(strings) to hold files, Array of(thistypeofthing or maybe change process) directories --
    private ArrayList<String> files; // this could be your files, but think before u do that what it breaks
    private ArrayList<FileSystem> directories;


    public FileSystem(){ // default
        pid = -1;
        priority = -1;
        drive = Drive.C;
        files = new ArrayList<String>();
        directories = new ArrayList<>();
    }

    public FileSystem(Drive drive){ // default
        pid = -1;
        priority = -1;
        this.drive = drive;
        files = new ArrayList<>();
        directories = new ArrayList<>();
    }



    public FileSystem(int pid, int priority, Drive drive){ //standard instantiation
        this.pid = pid;
        this.priority = priority;
        this.drive = drive;
        files = new ArrayList<>();
        directories = new ArrayList<>();
    }

    public FileSystem(int pid, int priority, Drive drive, ArrayList<String> files){ //standard instantiation
        this.pid = pid;
        this.priority = priority;
        this.drive = drive;
        this.files = new ArrayList<String>(files);
        directories = new ArrayList<>();
    }


    public FileSystem(FileSystem other){ // copy instantiation
        this.pid = other.pid;
        this.priority = other.priority;
        this.drive = other.drive;
    }

    //equals for my FileSystem
    @Override
    public boolean equals(Object ob){
        if(ob == null) return false;
        else if (!(ob instanceof FileSystem)) return false;
        else return this.pid == ((FileSystem)ob).pid;
    }

    //same deal but defining hashing
    @Override // prob dont need this
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.pid;
        hash = 79 * hash + this.priority;
        hash = 79 * hash + Objects.hashCode(this.drive);
        return hash;
    }

    //again but compare
    @Override
    public int compareTo(Object ob){
        if(this.priority == ((FileSystem)ob).priority)
            return 0;
        else if(this.priority < ((FileSystem)ob).priority)
            return 1;
        else return -1;
    }

    public void addDirectory(FileSystem fs){ // put the thing in there
      directories.add(fs);
      return;
    }
    public FileSystem getDirectory(int i){
      return directories.get(i);
    }
    public ArrayList<FileSystem> getDirs(){
      return directories;
    }

    //getters and setter

    public void addFile(String f){
      files.add(f);
      return;
    }
    public String getFile(int i){
      return files.get(i);
    }
    public ArrayList<String> getFiles(){
      return files;
    }

    public int getPid() {return this.pid;}

    public int getPriority() {return this.priority;}

    public Drive getDrive() {return this.drive;}

    public void setDrive(Drive drive) {this.drive = drive;}


}
