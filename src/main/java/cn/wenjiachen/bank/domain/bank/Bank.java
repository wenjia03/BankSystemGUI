package cn.wenjiachen.bank.domain.bank;

/**
 * @author Wenjia Chen
 * @date 2023/1/30:01
 */
public class Bank {
    public String BankName = null;
    public String BankID = null;
    private String BankSWIFTID = null;

    public Bank() {
    }

    public Bank(String bankName, String bankID, String bankSWIFTID) {
        BankName = bankName;
        BankID = bankID;
        BankSWIFTID = bankSWIFTID;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getBankID() {
        return BankID;
    }

    public void setBankID(String bankID) {
        BankID = bankID;
    }

    public String getBankSWIFTID() {
        return BankSWIFTID;
    }

    public void setBankSWIFTID(String bankSWIFTID) {
        BankSWIFTID = bankSWIFTID;
    }
}
