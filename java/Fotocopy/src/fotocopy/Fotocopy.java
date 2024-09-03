package fotocopy;
public class Fotocopy {
    
    public int jumlahLembar; //atributnya disini
    public boolean isWarna;
    public boolean isMember;
    
    public Fotocopy(int bar, boolean war, boolean mem){
        this.jumlahLembar = bar;
        this.isWarna = war;
        this.isMember = mem;
    } //konstruktor
    
    public int CashInfo(){ //method harganya
        int OneLembar;
        if(isWarna){
            OneLembar = 500; //berwarna
        } else {
            OneLembar = 200; //hitamputih
        }
        
        int TotalNya = jumlahLembar * OneLembar;
        return TotalNya;
    }
    
    public int Dizconut(){ //menghitung besarnya diskon
        int TotalNya = CashInfo(); //bagian Total dan Harga penghitungan
        
        if (isMember){
            double discount = (double) TotalNya * 0.10;
            return (int) discount;
        } else {
            return 0;
        }
    }
    
    //bagian menampilkan data
    public void tMPILdata(){
        System.out.println("Jumlah lembarnya :   " + jumlahLembar + "lEMBAR");
        
        String jPHOTO = isWarna ? "berwarna" : "hitam putih"; //tampilkan jenis warnanya ture atau false alias hitam putih
        System.out.println("Berwarna atau Hitam Putih :   " + jPHOTO);
        
        String InfoMEMBER = isMember ? "dia member" : "dia orang biasa";
        System.out.println("terdaftar Member atau tidak :   " + InfoMEMBER);
        
        int TotalNya = CashInfo();
        int discount = Dizconut();
        
        System.out.println("Harganya :   Rp. " + TotalNya);
        System.out.println("Besarnya Discount :   Rp. " + discount);
        
        int afterDiscount = TotalNya - discount;
        System.out.println("JAdi Total harga :   Rp. " + afterDiscount);
    }
    
    public static void main(String[] args) {
        Fotocopy poto = new Fotocopy (100, true, true);
        
        Fotocopy potos = new Fotocopy (50, false, false);
        
        System.out.println("Data Orang Ke 1 :");
        poto.tMPILdata();
        
        System.out.println("\n Data Orang Ke 2 :");
        potos.tMPILdata();
    }
    
}
