package FileService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import AuthService.Admin;
import AuthService.User;
import BloodBank.Blood;

public class FileService {

    private static String USER_FILE = "users.txt";
    private static String ADMIN_FILE = "admins.txt";
    private static String BLOOD_FILE = "blood.txt";
    private static String RECORD_FILE = "records.txt";
    private static File BLOOD_WAITING_FILE = new File("blood.txt");

    public static void writeIntoFile(File file, String data, boolean isOverWrite) {
        try {
            File userDir = file.getParentFile();
            if (userDir != null) {
                if (!userDir.exists()) {
                    userDir.mkdirs();
                }
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter fileWriter;
            if (isOverWrite) {
                fileWriter = new BufferedWriter(new FileWriter(file));
            } else {
                fileWriter = new BufferedWriter(new FileWriter(file, true));
            }
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            fileWriter.write(data);

            fileReader.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static User searchUser(String userName, String password) {
        try {
            File User_file = new File(USER_FILE);
            if (User_file.exists()) {
                User user = new User();
                BufferedReader fileReader = new BufferedReader(new FileReader(User_file));
                String line;
                while ((line = fileReader.readLine()) != null) {
                    String[] userCredentials = line.split(",");
                    if (userName.equals(userCredentials[0]) && password.equals(userCredentials[1])) {
                        System.out
                                .println(userCredentials[0] + " " + userCredentials[1] + " " + userCredentials[2] + " "
                                        + userCredentials[3] + " " + userCredentials[4] + " " + userCredentials[5] + " "
                                        + userCredentials[6]);
                        user.setUserDetails(userCredentials[0], userCredentials[1],
                                Integer.parseInt(userCredentials[2]),
                                userCredentials[3], userCredentials[4], userCredentials[5], userCredentials[6]);

                        break;
                    }
                }
                fileReader.close();
                return user.isUser();
            } else {
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static User searchUserByAdhaar(String adhaar_number) {
        try {
            File User_file = new File(USER_FILE);

            if (User_file.exists()) {
                User user = new User();
                BufferedReader fileReader = new BufferedReader(new FileReader(User_file));
                String line;
                while ((line = fileReader.readLine()) != null) {
                    String[] userCredentials = line.split(",");
                    if (adhaar_number.equals(userCredentials[3])) {
                        user.setUserDetails(userCredentials[0], userCredentials[1],
                                Integer.parseInt(userCredentials[2]),
                                userCredentials[3], userCredentials[4], userCredentials[5], userCredentials[6]);

                        break;
                    }
                }
                fileReader.close();
                return user.isUser();
            } else {
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static Admin searchAdmin(String userName, String password) {
        try {
            File Admin_file = new File(ADMIN_FILE);

            if (Admin_file.exists()) {
                Admin admin = new Admin();
                BufferedReader fileReader = new BufferedReader(new FileReader(Admin_file));
                String line;
                while ((line = fileReader.readLine()) != null) {
                    String[] adminCredentials = line.split(",");
                    System.out.println(adminCredentials);
                    if (userName.equals(adminCredentials[0]) && password.equals(adminCredentials[1])) {
                        admin.setAdminDetails(adminCredentials[0], adminCredentials[1]);
                        if (adminCredentials[2].equals("true")) {
                            admin.setApproved();
                        } else {
                            admin.setUnApproved();
                        }
                        break;
                    }
                }

                fileReader.close();
                return admin.isAdmin();
            } else {
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static void addUser(User user) {
        File User_file = new File(USER_FILE);

        writeIntoFile(User_file, user.toString() + "\n", false);
    }

    public static void addAdmin(Admin admin) {
        File Admin_file = new File(ADMIN_FILE);
        writeIntoFile(Admin_file, admin.toString() + "\n", false);
    }

    public static void addBlood(Blood blood) {
        try {
            File Blood_file = new File(BLOOD_FILE);
            if (Blood_file.exists()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(Blood_file));
                String line;
                String data = "";
                while ((line = fileReader.readLine()) != null) {
                    String[] bloodInfo = line.split(",");
                    if (bloodInfo[1].equals(blood.type)) {
                        int quantity = Integer.parseInt(bloodInfo[2]) + blood.quantity;
                        String newdata = bloodInfo[0] + "," + bloodInfo[1] + "," + quantity + "\n";
                        data += newdata;
                    } else {
                        data += line + "\n";
                    }
                }
                writeIntoFile(Blood_file, data, true);
                fileReader.close();
            }
        } catch (

        Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void addBloodWaiting(Blood blood) {
        writeIntoFile(BLOOD_WAITING_FILE, blood + "\n", false);
    }

    public static void aproveBlood() {
        try {
            if (BLOOD_WAITING_FILE.exists()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(BLOOD_WAITING_FILE));
                String line;
                while ((line = fileReader.readLine()) != null) {
                    addBlood(line);
                }
                fileReader.close();
                BLOOD_WAITING_FILE.delete();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Blood[] readBloodRecord() {
        Blood[] bloods = new Blood[8];
        try {
            File Blood_file = new File(BLOOD_FILE);
            if (Blood_file.exists()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(Blood_file));
                String line;
                while ((line = fileReader.readLine()) != null) {
                    String[] bloodDetails = line.split(",");
                    Blood blood = new Blood();
                    blood.setDetails(bloodDetails[0], Integer.parseInt(bloodDetails[1]));
                    bloods[Integer.parseInt(bloodDetails[0])] = blood;
                }
                fileReader.close();
                return bloods;
            } else {
                ArrayList<String[]> bloodsList = new ArrayList<String[]>() {
                    {
                        add(new String[] { "0", "A+", "0" });
                        add(new String[] { "1", "A-", "0" });
                        add(new String[] { "2", "B+", "0" });
                        add(new String[] { "3", "B-", "0" });
                        add(new String[] { "4", "AB+", "0" });
                        add(new String[] { "5", "AB-", "0" });
                        add(new String[] { "6", "O+", "0" });
                        add(new String[] { "7", "O-", "0" });
                    }
                };
                for (String[] bloodDetails : bloodsList) {

                    Blood blood = new Blood();
                    blood.setDetails(bloodDetails[1], Integer.parseInt(bloodDetails[2]));
                    bloods[Integer.parseInt(bloodDetails[0])] = blood;
                    writeIntoFile(Blood_file, bloodDetails[0] + "," + bloodDetails[1] + "," + bloodDetails[2] + "\n",
                            false);
                }
                return bloods;
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static int unApprovedAdmins() {
        try {
            File Admin_file = new File(ADMIN_FILE);

            if (Admin_file.exists()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(Admin_file));
                String line;
                int count = 0;
                while ((line = fileReader.readLine()) != null) {
                    String[] adminCredentials = line.split(",");
                    if (adminCredentials[2].equals("false")) {
                        System.out.println(count + ": " + adminCredentials[0]);
                    }
                    count++;
                }
                fileReader.close();
                return count;
            } else {
                System.out.println("No unapproved admins");

                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }

    public static void approveAdmin(int adminId) {
        try {
            File Admin_file = new File(ADMIN_FILE);
            if (Admin_file.exists()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(Admin_file));
                String line;
                String data = "";
                int count = 0;
                while ((line = fileReader.readLine()) != null) {
                    String[] adminCredentials = line.split(",");
                    if (adminCredentials[2].equals("false")) {
                        if (count == adminId) {
                            adminCredentials[2] = "true";
                            String newdata = adminCredentials[0] + "," + adminCredentials[1] + "," + adminCredentials[2]
                                    + "\n";
                            data += newdata;
                        } else {
                            data += line + "\n";
                        }
                    } else {
                        data += line + "\n";
                    }
                    count++;
                }
                writeIntoFile(Admin_file, data, true);
                fileReader.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void addRecord(Blood blood, User user) {
        try {
            File recordFile = new File(RECORD_FILE);
            if (!recordFile.exists()) {
                recordFile.createNewFile();
            }
            writeIntoFile(recordFile, user.getRecordString() + "," + blood.toString() + "\n", false);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void checkBloodDetails() {
        try {
            File Blood_file = new File(BLOOD_FILE);
            if (Blood_file.exists()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(Blood_file));
                String line;
                while ((line = fileReader.readLine()) != null) {
                    String[] bloodDetails = line.split(",");
                    System.out.println(bloodDetails[1] + " : " + bloodDetails[2]);
                }
                fileReader.close();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void printUserRecord(User user) {
        try {
            File recordFile = new File(RECORD_FILE);
            if (recordFile.exists()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(recordFile));
                String line;
                boolean flag = false;
                while ((line = fileReader.readLine()) != null) {
                    String[] record = line.split(",");
                    if (record[3].equals(user.adhaar_number)) {
                        System.out.println(
                                record[5] + " " + record[6]);
                        flag = true;
                    }
                }
                fileReader.close();

                if (!flag) {
                    System.out.println("Donate blood to see the record");
                }
            } else {
                System.out.println("Donate blood to see the record");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void printAllRecords() {
        try {
            File recordFile = new File(RECORD_FILE);
            if (recordFile.exists()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(recordFile));
                String line;
                while ((line = fileReader.readLine()) != null) {
                    String[] record = line.split(",");
                    System.out
                            .println(record[0] + " " + record[1] + " " + record[2] + " " + record[3] + " " + record[4]
                                    + " " +
                                    record[5] + " " + record[6]);
                }
                fileReader.close();
            } else {
                System.out.println("No Records Found");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
