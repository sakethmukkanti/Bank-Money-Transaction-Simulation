//     Run the following command to compile and run 
//     javac gnb.java && java gnb

import static java.lang.Math.random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;
class Node { 
    String id; 
    String money;
    Node next; 
    ReentrantLock nl;
    Node(String d,String m,Node n,ReentrantLock l) { id = d;money=m;next=n;nl=l; } 
} 
class Nodea{
    String a;
    String b;
    String c;
    String d;
}
class guwahati_national_bank {
    private Node[] head;
    private Integer[] acn;
    public Nodea[] upd;
    public guwahati_national_bank(){
        this.head = new Node[10];
        this.acn= new Integer[10];
        this.upd =new Nodea[1000000];
        for(int i=0;i<10;i++){
            ReentrantLock temp= new ReentrantLock();
            head[i] = new Node(String.valueOf(i)+"000000000","0",null,temp);
            acn[i] = 1;
        }   
        for(int i=0;i<1000000;i++){
            upd[i]= new Nodea();
        }
    }
    class updations implements Runnable{
        int s;
        int tid;
        public updations(int s,int tid){
            this.s=s;
            this.tid=tid;
        }
        @Override
        public void run(){
            for(int i=0;i<s;i++){
               // System.out.println(upd[tid*s+i].a);
                if(upd[tid*s+i].a.equals("3")){
                    //System.out.println(i);
                    //System.out.println("3)"+"\t");
                    add_user(upd[tid*s+i].b,upd[tid*s+i].c);
                }
                if(upd[tid*s+i].a.equals("0")){
                    //System.out.println("0)"+"\t");
                    deposit(upd[tid*s+i].b,upd[tid*s+i].c);
                }
                if(upd[tid*s+i].a.equals("1")){
                    //System.out.println("1)"+"\t");
                    withdraw(upd[tid*s+i].b,upd[tid*s+i].c);
                }
                if(upd[tid*s+i].a.equals("4")){
                    //System.out.println("4)"+"\t");
                    remove(upd[tid*s+i].b);
                }
                if(upd[tid*s+i].a.equals("5")){
                    //System.out.println("5)"+"\t");
                    transfer_account(upd[tid*s+i].b,upd[tid*s+i].c);
                }
                if(upd[tid*s+i].a.equals("2")){
                    //System.out.println("2)"+"\t");
                    transfer_money(upd[tid*s+i].b,upd[tid*s+i].c,upd[tid*s+i].d);
                }
            }
        }
        public void add_user(String b,String m){
            int br=Integer.valueOf(b);
            head[br].nl.lock();
            //System.out.println(ac);
            Node pred=head[br];
            String ac;
            try{
                Node curr=pred.next;
                if(curr!=null){curr.nl.lock();}
                try{
                    while(curr!=null ){
                        pred.nl.unlock();
                        pred=curr;
                        curr=curr.next;
                        if(curr!=null){curr.nl.lock();}
                    }
                    ac=String.valueOf(acn[br]);
                    while(ac.length()<9){
                        ac="0"+ac;
                    }
                    ac=String.valueOf(br)+ac;
                    ReentrantLock temp = new ReentrantLock();
                    Node node= new Node(ac,m,null,temp);
                    if(acn[br]>10000){
                        System.out.println("new account with account number "+ac+" had created"+"\n");
                    }
                    //System.out.println(ac+"\t"+m+"\n");
                    acn[br]++;
                    pred.next=node;
                }
                finally{
                    if(curr!=null){curr.nl.unlock();}
                }
            }
            finally{
                pred.nl.unlock();
            } 
        }
        public void deposit(String ac,String m){
            int br=Integer.parseInt(String.valueOf(ac.charAt(0))); 
            head[br].nl.lock();
            //System.out.println(ac);
            Node pred=head[br];
            try{
                Node curr=pred.next;
                if(curr!=null){curr.nl.lock();}
                try{
                    while(curr!=null && !curr.id.equals(ac)){
                        pred.nl.unlock();
                        pred=curr;
                        curr=curr.next;
                        if(curr!=null){curr.nl.lock();}
                    }
                    if(curr==null){
                        System.out.println("no account to deposit \n");
                    }
                    else{
                        String x=curr.money;
                        curr.money=String.valueOf(Integer.valueOf(m)+Integer.valueOf(curr.money));
                        System.out.println("money changed from "+x+" to "+curr.money+" in account "+ac+"\n");
                    }
                }
                finally{
                    if(curr!=null){curr.nl.unlock();}
                }
            }
            finally{
                pred.nl.unlock();
            }
        }
        public void withdraw(String ac,String m){
            int br=Integer.parseInt(String.valueOf(ac.charAt(0))); 
            head[br].nl.lock();
            //System.out.println(ac);
            Node pred=head[br];
            try{
                Node curr=pred.next;
                if(curr!=null){curr.nl.lock();}
                try{
                    while(curr!=null && !curr.id.equals(ac)){
                        pred.nl.unlock();
                        pred=curr;
                        curr=curr.next;
                        if(curr!=null){curr.nl.lock();}
                    }
                    if(curr==null){
                        System.out.println("no account to withdraw \n");
                    }
                    else{
                        String x=curr.money;
                        if(Integer.valueOf(m)<=Integer.valueOf(curr.money)){
                            curr.money=String.valueOf(Integer.valueOf(curr.money)-Integer.valueOf(m));
                            System.out.println("amount "+m+" debited from account "+ac+"\n");
                        }
                        else{
                            System.out.println("Insufficient balance to withdraw from "+ac+"\n");
                        }
                    }
                }
                finally{
                    if(curr!=null){curr.nl.unlock();}
                }
            }
            finally{
                pred.nl.unlock();
            }
        }
        public void remove(String ac){
            int br=Integer.parseInt(String.valueOf(ac.charAt(0))); 
            head[br].nl.lock();
            //System.out.println(ac);
            Node pred=head[br];
            try{
                Node curr=pred.next;
                if(curr!=null){curr.nl.lock();}
                try{
                    while(curr!=null && !curr.id.equals(ac)){
                        pred.nl.unlock();
                        pred=curr;
                        curr=curr.next;
                        if(curr!=null){curr.nl.lock();}
                    }
                    if(curr==null){
                        System.out.println("no account to remove from "+br+" branch \n");
                    }
                    else{
                        Node nex=curr.next;
                        if(nex!=null){
                            nex.nl.lock();
                            pred.next=nex;
                            nex.nl.unlock();
                        }   
                        else{
                            pred.next=null;
                        }
                        System.out.println("account "+ac+" removed from guwahati National bank"+"\n");
                    }
                }
                finally{
                    if(curr!=null){curr.nl.unlock();}
                }
            }
            finally{
                pred.nl.unlock();
            }   
        }
        public void transfer_account(String ac1,String b2){
            int br1,br2;
            String m="0";
            br1=Integer.parseInt(String.valueOf(ac1.charAt(0)));
            br2=Integer.valueOf(b2);
            if(br1<br2){
                head[br1].nl.lock();
                head[br2].nl.lock();
            }
            else{
                head[br2].nl.lock();
                head[br1].nl.lock();
            }
            boolean flag=false;
            //System.out.println(ac);
            Node pred=head[br1];
            try{
                Node curr=pred.next;
                if(curr!=null){curr.nl.lock();}
                try{
                    while(curr!=null && !curr.id.equals(ac1)){
                        pred.nl.unlock();
                        pred=curr;
                        curr=curr.next;
                        if(curr!=null){curr.nl.lock();}
                    }
                    if(curr==null){
                        System.out.println("no account to transfer from "+br1+" branch \n");
                        flag=true;
                    }
                    else{
                        Node nex=curr.next;
                        m=curr.money;
                        if(nex!=null){
                            nex.nl.lock();
                            pred.next=nex;
                            nex.nl.unlock();
                        }   
                        else{
                            pred.next=null;
                        }
                        
                    }
                }
                finally{
                    if(curr!=null){curr.nl.unlock();}
                }
            }
            finally{
                pred.nl.unlock();
            }   
            if(flag){
                head[br2].nl.unlock();
                return;
            }
            //System.out.println(ac);
            pred=head[br2];
            String ac;
            try{
                Node curr=pred.next;
                if(curr!=null){curr.nl.lock();}
                try{
                    while(curr!=null ){
                        pred.nl.unlock();
                        pred=curr;
                        curr=curr.next;
                        if(curr!=null){curr.nl.lock();}
                    }
                    ac=String.valueOf(acn[br2]);
                    while(ac.length()<9){
                        ac="0"+ac;
                    }
                    ac=String.valueOf(br2)+ac;
                    ReentrantLock temp = new ReentrantLock();
                    Node node= new Node(ac,m,null,temp);
                    System.out.println("account "+ac1+" transferred from branch "+br1+" to "+br2+" with new account number "+ac+"\n");
                    acn[br2]++;
                    pred.next=node;
                }
                finally{
                    if(curr!=null){curr.nl.unlock();}
                }
            }
            finally{
                pred.nl.unlock();
            } 

        }
        public void transfer_money(String ac1,String ac2,String m){
            int br1,br2;
            br1=Integer.parseInt(String.valueOf(ac1.charAt(0)));
            br2=Integer.parseInt(String.valueOf(ac2.charAt(0)));
            if(br1<br2){
                head[br1].nl.lock();
                head[br2].nl.lock();
            }
            else{
                head[br2].nl.lock();
                head[br1].nl.lock();
            }
            boolean flag=false;
            Node pred=head[br1];
            try{
                Node curr=pred.next;
                if(curr!=null){curr.nl.lock();}
                try{
                    while(curr!=null && !curr.id.equals(ac1)){
                        pred.nl.unlock();
                        pred=curr;
                        curr=curr.next;
                        if(curr!=null){curr.nl.lock();}
                    }
                    if(curr==null){
                        System.out.println("no account to withdraw \n");
                        flag=true;
                    }
                    else{
                        String x=curr.money;
                        if(Integer.valueOf(m)<=Integer.valueOf(curr.money)){
                            curr.money=String.valueOf(Integer.valueOf(curr.money)-Integer.valueOf(m));
                            //System.out.println("amount "+m+" debited from account "+ac+"\n");
                        }
                        else{
                            System.out.println("Insufficient balance to transfer from "+ac1+" to "+ac2+"\n");
                            flag=true;
                        }
                    }
                }
                finally{
                    if(curr!=null){curr.nl.unlock();}
                }
            }
            finally{
                pred.nl.unlock();
            }
            if(flag){
                head[br2].nl.unlock();
                return;
            }
            pred=head[br2];
            try{
                Node curr=pred.next;
                if(curr!=null){curr.nl.lock();}
                try{
                    while(curr!=null && !curr.id.equals(ac2)){
                        pred.nl.unlock();
                        pred=curr;
                        curr=curr.next;
                        if(curr!=null){curr.nl.lock();}
                    }
                    if(curr==null){
                        System.out.println("the account we are trying to transfer is not there \n");
                        head[br1].nl.lock();
                        Node pred2=head[br1];
                        try{
                            Node curr2=pred2.next;
                            if(curr2!=null){curr2.nl.lock();}
                            try{
                                while(curr2!=null && !curr2.id.equals(ac1)){
                                    pred2.nl.unlock();
                                    pred2=curr2;
                                    curr2=curr2.next;
                                    if(curr2!=null){curr2.nl.lock();}
                                }
                
                                    curr2.money=String.valueOf(Integer.valueOf(m)+Integer.valueOf(curr2.money));
                                    //System.out.println("money changed from "+x+" to "+curr.money+" in account "+ac+"\n");

                            }
                            finally{
                                if(curr2!=null){curr2.nl.unlock();}
                            }
                        }
                        finally{
                            pred2.nl.unlock();
                        }

                    }
                    else{
                        curr.money=String.valueOf(Integer.valueOf(m)+Integer.valueOf(curr.money));
                        System.out.println("money "+m+" had transferred from account "+ac1+" to "+ac2+"\n");
                    }
                }
                finally{
                    if(curr!=null){curr.nl.unlock();}
                }
            }
            finally{
                pred.nl.unlock();
            }


        }

    }
    public void update(){
        for(int i=0;i<1000;i++){
            upd[i].a=String.valueOf((int) (random() * 6));
            if(upd[i].a.equals("0")){
                String p=String.valueOf((int) (random() * 10));
                upd[i].b=String.valueOf((int) (random() * 10000));
                while(upd[i].b.length()<9){
                    upd[i].b="0"+upd[i].b;
                }
                upd[i].b=p+upd[i].b;
                upd[i].c=String.valueOf((int) (random() * 50000));
            }
            if(upd[i].a.equals("1")){
                String p=String.valueOf((int) (random() * 10));
                upd[i].b=String.valueOf((int) (random() * 10000));
                while(upd[i].b.length()<9){
                    upd[i].b="0"+upd[i].b;
                }
                upd[i].b=p+upd[i].b;
                upd[i].c=String.valueOf((int) (random() * 20000));
            }
            if(upd[i].a.equals("3")){
                upd[i].b=String.valueOf((int) (random() * 10));
                upd[i].c=String.valueOf((int) (random() * 100000));
            }
            if(upd[i].a.equals("4")){
                String p=String.valueOf((int) (random() * 10));
                upd[i].b=String.valueOf((int) (random() * 10000));
                while(upd[i].b.length()<9){
                    upd[i].b="0"+upd[i].b;
                }
                upd[i].b=p+upd[i].b;   
            }
            if(upd[i].a.equals("5")){
                int p1,p2;
                p1= (int) (random() * 10);
                p2=(int) (random() * 10);
                while(p1==p2){
                    p1=(int) (random() * 10);
                }
                upd[i].b=String.valueOf((int) (random() * 10000));
                while(upd[i].b.length()<9){
                    upd[i].b="0"+upd[i].b;
                }
                upd[i].b=String.valueOf(p1)+upd[i].b; 
                upd[i].c=String.valueOf(p2); 
            }
            if(upd[i].a.equals("2")){
                int p1,p2;
                p1= (int) (random() * 10);
                p2=(int) (random() * 10);
                while(p1==p2){
                    p1=(int) (random() * 10);
                }
                upd[i].b=String.valueOf((int) (random() * 10000));
                while(upd[i].b.length()<9){
                    upd[i].b="0"+upd[i].b;
                }
                upd[i].b=String.valueOf(p1)+upd[i].b;
                upd[i].c=String.valueOf((int) (random() * 10000));
                while(upd[i].c.length()<9){
                    upd[i].c="0"+upd[i].c;
                }
                upd[i].c=String.valueOf(p2)+upd[i].c;
                upd[i].d=String.valueOf((int) (random() * 50000));   
            }
        }
        ExecutorService executor = Executors.newWorkStealingPool(100);
        for(int i=0;i<100;i++){
            Runnable worker = new updations(10,i);
            executor.execute(worker);
        }
        executor.shutdown();
		while (!executor.isTerminated()) {
        }


    }

    public void initialize(){
        int k=0;

        for(int i=0;i<10;i++){
            for(int j=1;j<=10000;j++){
                upd[k].a="3";
                upd[k].b=String.valueOf(i);
                upd[k].c=String.valueOf((int) (random() * 100000));
                upd[k].d="0";
                k++;
            }
        }
        ExecutorService executor = Executors.newWorkStealingPool(100);
        for(int i=0;i<100;i++){
            Runnable worker = new updations(1000,i);
            executor.execute(worker);
        }
        executor.shutdown();
		while (!executor.isTerminated()) {
        }
        //Node temp=head[1];
        /*while(temp.next!=null){

            System.out.println(temp.id+"\n");
            temp=temp.next;
        }*/

    }

}

public class gnb{
    public static void main(String[] args){
        guwahati_national_bank obj = new guwahati_national_bank();
        long startTime = System.currentTimeMillis();
        obj.initialize();  
        obj.update();
        long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime - startTime) + " milliseconds");


    }   
}