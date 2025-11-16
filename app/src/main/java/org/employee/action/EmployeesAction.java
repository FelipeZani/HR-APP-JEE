package org.employee.action;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.employee.dea.EmployeeDAO;

public abstract class EmployeesAction implements Action {

    public static String hashString(String passwordToHash) {


        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(passwordToHash.getBytes());

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public enum Rank {
        JUNIOR,
        MIDLEVEL,
        SENIOR;

        @Override
        public String toString() {
            switch (this.ordinal()) {
                case 0:
                    return "JUNIOR";
                case 1:
                    return "MIDLEVEL";
                case 2:
                    return "SENIOR";
                default:
                    return null;
            }
        }

        public static Rank getRank(String rank) {
            switch (rank) {
                case "junior":
                    return JUNIOR;
                case "midlevel":
                    return MIDLEVEL;
                case "senior":
                    return SENIOR;
                default:
                    return null;
            }
        }

    }

    protected static EmployeeDAO dao = new EmployeeDAO();

    public static EmployeeDAO getDao() {
        return dao;
    }

}
