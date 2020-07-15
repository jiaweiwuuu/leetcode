package interview;


class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

public class HighFrequencyOne {
    /*
     * 25. K 个一组翻转链表
     */

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode fakeHead = new ListNode();
        ListNode p = head;
        if(k == 0){
            return head;
        }

        fakeHead.next = head;
        p = fakeHead;
        ListNode pre = head;

        if( head == null || head.next == null){
            return head;
        }

        ListNode cur = pre.next;

        while(true){
            ListNode check = pre;

            for(int i =0; i < k; i++){
                if(check == null){
                    return fakeHead.next;
                }
                check = check.next;
            }

            cur = pre.next;

            for(int i = 2; i <=k; i++){
                pre.next = cur.next;
                cur.next = p.next;
                p.next = cur;
                cur = pre.next;
            }

            p = pre;
            pre = cur;

        }

    }


    public static void main(String[] args) {

    }
}
