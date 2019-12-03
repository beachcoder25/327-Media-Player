/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Phase4_TwoPhaseCommit;

/**
 *
 * @author nicka
 */
public class Transaction {
    public enum Operation { WRITE, DELETE };
    public enum Vote { YES, NO};
    Long TransactionId;
    Vote vote;
    String fileName;
    Long pageIndex;
}
