//Liam Robb, lr003
import java.util.Collections;
import java.util.Vector;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.*;

public class Dispatcher extends javax.swing.JFrame {

    private static int pidCounter;

    // ** add a 'where we are int' to simulate level of file system
    private int level = 0; // 0-5, or really 0 - 2ish bc who has the time
    //
    private int fi = 0; // file indicator
    private int di = 0; // directory indicator

    private ArrayList<FileSystem> ready; // maybe can have three one for a,b,and c
    private ArrayList<FileSystem> blocked; // may not need these to be array lists
    // private ArrayList<FileSystem> C; // the drives managed from here
    private FileSystem running;
    private FileSystem Adrive;
    private FileSystem Bdrive;
    private FileSystem Cdrive;

    public Dispatcher() {
        initComponents();
        pidCounter = 10;
        ready = new ArrayList<>();
        blocked = new ArrayList<>();
        running = new FileSystem(pidCounter, 30, Drive.A);
        // what we wanna do is set up one arraylist with three FileSystem, or instantaite running with one FS
        // we could even populate the FS's here
        Adrive = new FileSystem(Drive.A);

        Bdrive = new FileSystem(Drive.B);

        Cdrive = new FileSystem(Drive.C);



        ++pidCounter;
        initialSet();
        drawTable();
    }


    @SuppressWarnings("unchecked")

    private void initComponents() {

        mySeperator = new javax.swing.JSeparator();
        addproc = new javax.swing.JButton();
        procLabel = new javax.swing.JLabel();
        scroller = new javax.swing.JScrollPane();
        firstTable = new javax.swing.JTable();
        controlArea = new javax.swing.JLabel();
        processBlock = new javax.swing.JButton();
        processUnblock = new javax.swing.JButton();
        processKill = new javax.swing.JButton();
        endAll = new javax.swing.JButton();
        inputPriority = new javax.swing.JTextField();
        reset = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        labelBig = new javax.swing.JLabel();
        timeExceeded = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        addproc.setText("Add");
        addproc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProcessButtonPress(evt);
            }
        });

        procLabel.setText("Add File");

        firstTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] { // 2d array

                },
                new String [] {  // heading and define length of first array
                        "Current Directory Contents", "FS actual Drive", ""
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroller.setViewportView(firstTable);
        if (firstTable.getColumnModel().getColumnCount() > 0) {
            firstTable.getColumnModel().getColumn(0).setResizable(false);
            firstTable.getColumnModel().getColumn(1).setResizable(false);
            firstTable.getColumnModel().getColumn(2).setResizable(false);
        }

        controlArea.setText("Control Panel");

        processBlock.setText("Print File");//Block Selected"); //
        processBlock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blockProcessButtonPress(evt);
            }
        });

        timeExceeded.setText("--------"); // "time exceeded"
        timeExceeded.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeSliceExceededButtonPress(evt);
            }
        });


        processUnblock.setText("Enter Dir");//"Unblock Selected"); //
        processUnblock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unblockProcessButtonPress(evt);
            }
        });

        processKill.setText("Delete File");
        processKill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                killProcessButtonPress(evt);
            }
        });

        endAll.setText("reset");
        endAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                killsAllButtonPress(evt);
            }
        });

        inputPriority.setText("File Contents");

        reset.setText("Parent Dir");//"Reset"); //
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonPress(evt);
            }
        });

        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonPress(evt);
            }
        });

        labelBig.setText("FS and VFS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(scroller)
                                        .addComponent(mySeperator)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(20, 20, 20)
                                                                                .addComponent(inputPriority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(addproc))
                                                                        .addComponent(labelBig))
                                                                .addGap(170, 170, 170)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)//processBlock next line
                                                                        .addComponent(processBlock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(processUnblock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(48, 48, 48)
                                                                .addComponent(procLabel)))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(controlArea)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(processKill, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(endAll, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(timeExceeded, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                            //

                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scroller, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mySeperator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(controlArea)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(processBlock)
                                                        .addComponent(processKill)
                                                        .addComponent(reset)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(procLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(addproc)
                                                        .addComponent(inputPriority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(processUnblock)
                                                        .addComponent(timeExceeded)
                                                        .addComponent(endAll)
                                                        .addComponent(exit))
                                                .addGap(11, 11, 11))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(labelBig)
                                                .addContainerGap())))
        );

        pack();
    }
    // Kills all processes.

    private void killsAllButtonPress(java.awt.event.ActionEvent evt) { // maybe keep as a start over button
        ready.clear(); // ** could be repurposed as garb collector -- gutted --
        blocked.clear();
        running = null;
        DefaultTableModel model = (DefaultTableModel) firstTable.getModel();
        model.setRowCount(0);
        ready = new ArrayList<>();
        blocked = new ArrayList<>();
    }

    // Kills the selected Process (That is, the process currently highlighted on the table).
    // ** can be repurposed to deallocate memory, can we change pid to some null value
    private void killProcessButtonPress(java.awt.event.ActionEvent evt) { //seems mostly ok
        DefaultTableModel model = (DefaultTableModel) this.firstTable.getModel();
        int row = firstTable.getSelectedRow();
        Vector rowData = (Vector)model.getDataVector().elementAt(row);
        final int targetPid = (int)rowData.elementAt(0); // ** pid can be memory or still process ID
        // ** (int)rowData.elementAt(0) will give the status bit which we are looking for
        Drive d = (Drive)rowData.elementAt(2);
        model.removeRow(row); // this is where we might make diff
                              // could not remove it, and instead just change

        //Remove the process from ArrayList
        switch(d){ // **  probabaly can get rid of any row altering logic, or use this as part of garbo
            case A:
                running = null;
                running = contextSwitch();
                reDrawTable();
                break;

            case B:
                for (FileSystem p : ready) {
                    if(p.getPid() == targetPid){
                        ready.remove(p);
                        break;
                    }
                }
                break;

            case C:
                for (FileSystem p : blocked) {
                    if(p.getPid() == targetPid){
                        blocked.remove(p);
                        break;
                    }
                }
                break;

            default:
                System.err.println("Invalid status");
                break;
        }
    }

    // Adds a Process, given a priority, to the ready ArrayList.
    // ** this is changed to ask for memory

    private void addProcessButtonPress(java.awt.event.ActionEvent evt) {

        if(isInteger(inputPriority.getText(),10)){
            int getPriority = Integer.parseInt(inputPriority.getText());
            if(getPriority >= 0){
                // ** that's a one line solution, we might need more
                // ** this is where
                if(running == null)
                    running = new FileSystem(pidCounter,getPriority,Drive.A);
                else ready.add(new FileSystem(pidCounter,getPriority,Drive.B));
                ++pidCounter;
                reDrawTable();
            }
            else JOptionPane.showMessageDialog(null, "Please enter a positive integer value.");
        } // ** these effectively deal with bad input, as they exlude non integer and negatives

        else JOptionPane.showMessageDialog(null, "Please enter an integer value.");
    }

    // Resets the program to its initial state.

    private void resetButtonPress(java.awt.event.ActionEvent evt) {
        ready.clear();
        blocked.clear();
        running = null;
        DefaultTableModel model = (DefaultTableModel) firstTable.getModel();
        model.setRowCount(0);
        pidCounter = 10;
        ready = new ArrayList<>();
        blocked = new ArrayList<>();
        running = new FileSystem(pidCounter, 30, Drive.A);
        ++pidCounter;
        initialSet();
        drawTable();
    }

    // Exits the program.

    private void exitButtonPress(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }


    // time exceed context switch
    private void timeSliceExceededButtonPress(java.awt.event.ActionEvent evt) {
        running = contextSwitch2();
        reDrawTable();
    }

    // Blocks the selected process.

    private void blockProcessButtonPress(java.awt.event.ActionEvent evt) { //......

        JOptionPane.showMessageDialog(null, Adrive.getFile(0));
        /*
        DefaultTableModel model = (DefaultTableModel) this.firstTable.getModel();
        int row = firstTable.getSelectedRow();
        Vector rowData = (Vector)model.getDataVector().elementAt(row);


        //If blocking the running process, simply context switch
        if((Drive)rowData.elementAt(2) == Drive.A){
            running = contextSwitch();
        }

        //Otherwise move the selected process to the blocked list
        else{
            int targetPid = (int)rowData.elementAt(0);
            for (FileSystem p : ready) {
                if(p.getPid() == targetPid){
                    p.setDrive(Drive.C);
                    blocked.add(p);
                    ready.remove(p);
                    break;
                }
            }
        }
        reDrawTable(); */
    }

    //Resumes the currently selected blocked process.

    private void unblockProcessButtonPress(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) this.firstTable.getModel();
        int row = firstTable.getSelectedRow();
        Vector rowData = (Vector)model.getDataVector().elementAt(row);

        if((Drive)rowData.elementAt(2) == Drive.C){
            int targetPid = (int)rowData.elementAt(0);
            for (FileSystem p : blocked) {
                if(p.getPid() == targetPid){
                    p.setDrive(Drive.B);
                    ready.add(p);
                    blocked.remove(p);
                    break;
                }
            }
        }
        if(running == null)
            running = contextSwitch();
        reDrawTable();
    }
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dispatcher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dispatcher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dispatcher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dispatcher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Dispatcher().setVisible(true);
        });
    }

    // Populate table with a set of processes
    //>>>>>>> this is where we need to really work on

    public void populateTable(ArrayList<FileSystem> processes){
        DefaultTableModel model = (DefaultTableModel) firstTable.getModel();
        firstTable.setModel(model);
        // we need to know if we have to do all three of the drives, are we TLD - level == 0
        // switch(level){}
        // need arraylist of files and arraylist of FS's
        processes.stream().map((process) -> { // need to do this twice, this is the directories one
            Object[] row = new Object[2];
            row[0] = "Directory_" + String.valueOf(di);// process.getPid // strings work
            di++;

            //if(process.getDrive() == Drive.C)
            //    row[1] = -1;
            // again, we care not about priority, we want this to be F1,F2,D1,D2 etc
            //else row[1] = process.getPriority();


            row[1] = process.getDrive();
            return row;
        }).forEachOrdered((row) -> {
            model.addRow(row);
        });

        di = 0;
        fi = 0;
    }


    public void populateTableFiles(ArrayList<String> files, Drive d){
        DefaultTableModel model = (DefaultTableModel) firstTable.getModel();
        firstTable.setModel(model);
        // we need to know if we have to do all three of the drives, are we TLD - level == 0
        // switch(level){}
        // need arraylist of files and arraylist of FS's
        files.stream().map((file) -> { // need to do this twice, this is the directories one
            Object[] row = new Object[2];
            row[0] = "File_" + String.valueOf(fi);// process.getPid // strings work
            fi++;

            //if(process.getDrive() == Drive.C)
            //    row[1] = -1;
            // again, we care not about priority, we want this to be F1,F2,D1,D2 etc
            //else row[1] = process.getPriority();


            row[1] = d;
            return row;
        }).forEachOrdered((row) -> {
            model.addRow(row);
        });

        di = 0;
        fi = 0;
    }

    // Populates table with one process

    public void populateTable(FileSystem process){
        DefaultTableModel model = (DefaultTableModel) firstTable.getModel();
        firstTable.setModel(model);
        Object[] row = new Object[3];
        row[0] = process.getPid();
        row[1] = "Running";
        row[2] = process.getDrive();
        model.addRow(row);
    }

// Draw the table from  lists.
// Tear this shit apart, it needs to draw some directories and files
    public void drawTable(){
        if(running != null){
            //populateTable(running);
            Collections.sort(ready);
            // we now need to call this with current stuff
            populateTableFiles(Adrive.getFiles(), Drive.A);
            populateTable(Adrive.getDirs());//); ready); // just a fo now
            /*
            populateTableFiles(Bdrive.getFiles(), Drive.B);
            populateTable(Bdrive.getDirs());//); ready); // just a fo now
            populateTableFiles(Cdrive.getFiles(), Drive.C);
            populateTable(Cdrive.getDirs());//); ready); // just a fo now
            */
            populateTable(blocked);
        }
        else{
            Collections.sort(ready);
            populateTable(ready);
            populateTable(blocked);
        }
    }

    // Erases contents of the table and redraws it.

    public void reDrawTable(){
        DefaultTableModel model = (DefaultTableModel) firstTable.getModel();
        model.setRowCount(0);
        drawTable();
    }

    // Fills the sets with arbitrary initial values. >>>>>>

    public void initialSet(){ // gut for instantiation of drives
        // a Dog B town, how u been
        Adrive.addFile("Print this file out");// "first file");
        Adrive.addFile("second file");
        //Adrive.addFile("third file");
        //Adrive.addFile("third file");

        Adrive.addDirectory(new FileSystem(Drive.A));
        //Adrive.addDirectory(new FileSystem(Drive.A));
        //Adrive.addDirectory(new FileSystem(Drive.A));


        Bdrive.addFile("I'm a cool little file");
        Bdrive.addDirectory(new FileSystem(Drive.B));

        Cdrive.addFile("What will he write next?");
        Cdrive.addDirectory(new FileSystem(Drive.C));

        ready.add(new FileSystem(pidCounter, 20, Drive.B));
        ++pidCounter;
        ready.add(new FileSystem(pidCounter, 43, Drive.B));
        ++pidCounter;
        ready.add(new FileSystem(pidCounter, 18, Drive.B));
        ++pidCounter;
        ready.add(new FileSystem(pidCounter, 34, Drive.B));
        ++pidCounter;
    }

    // Switches the running process to the process on the Ready list with the highest priority unless the ready list is empty, in which case it returns null.

    public FileSystem contextSwitch(){
        if (ready.size() > 0){
            FileSystem newRunning = new FileSystem(ready.get(0));
            newRunning.setDrive(Drive.A);
            if(running != null){
                running.setDrive(Drive.C);
                blocked.add(running);
            }
            ready.remove(0);
            return newRunning;
        }
        else{
            if(running != null){
                running.setDrive(Drive.C);
                blocked.add(running);
            }
            return null;
        }
    }

    public FileSystem contextSwitch2(){ //for just context switching
        if (ready.size() > 0){
            FileSystem newRunning = new FileSystem(ready.get(0));
            newRunning.setDrive(Drive.A);
            ready.remove(0);
            if(running != null){
                running.setDrive(Drive.B);
                ready.add(running);
            }
            return newRunning;
        }
        else{
            if(running != null){
                running.setDrive(Drive.C);
                blocked.add(running);
            }
            return null;
        }
    }
    // input sanitizing

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }


    // declaring my gui variables
    private javax.swing.JButton processBlock;
    private javax.swing.JLabel controlArea;
    private javax.swing.JButton exit;
    private javax.swing.JButton endAll;
    private javax.swing.JButton processKill;
    private javax.swing.JLabel labelBig;
    private javax.swing.JTextField inputPriority;
    private javax.swing.JButton addproc;
    private javax.swing.JLabel procLabel;
    private javax.swing.JButton reset;
    private javax.swing.JScrollPane scroller;
    private javax.swing.JSeparator mySeperator;
    private javax.swing.JTable firstTable;
    private javax.swing.JButton processUnblock;
    private javax.swing.JButton timeExceeded;

}
