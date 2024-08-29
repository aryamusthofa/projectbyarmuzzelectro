
package motor;

public class motor {
public String kodeMtr;
    protected String merkMtr;
    String jenisMtr;
    public float kecepatan;
    public int gigi;
    public static int jumTotalMotor;
    public motor(){
        System.out.println("Obyek terbentuk dari konstruktor default");
        merkMtr = "KOSONG";
        kecepatan = gigi = 0;
    }
    public motor(int g){
        System.out.println("Obyek terbentuk dari konstruktor berparameter 1");
        merkMtr = "KOSONG";
        kecepatan = 0;
        gigi = 0;
    }
    public motor (float x){
        kecepatan = x;
    }
    public motor(int g, float v){
        System.out.println("Obyek terbentuk dari konstruktor berparameter 2");
        merkMtr = "KOSONG";
        kecepatan = v;
        gigi = g;
}
    public void start(){
        gigi = 1;
        kecepatan = 0;
        System.out.println("porseling awal = "+gigi);
    }
    private void kurangiPorsneling(){
        gigi--;
        System.out.println("kurangi poslening = +gigi");
    }
    private void tambahPorsneling(){
        gigi++;
        System.out.println("kurangi poslening = +gigi");
           
    }
    public void tambahVelocity(float v){
        kecepatan +=v;
        if (v>10)
            tambahPorsneling();
    }
    
    public void kurangVelocity(float v){
        kecepatan +=v;
        if (v>5.5)
            kurangiPorsneling();
    }
    public void stop (){
            kecepatan = 0;
            gigi = 0;
            System.out.println("berhenti = "+kecepatan);
    }
    public void tampilData() {
        System.out.println("KODE = "+kodeMtr);
        System.out.println("MEREK = "+merkMtr);
        System.out.println("JENIS = "+jenisMtr);
        System.out.println("KECEPATAN = "+kecepatan);
        System.out.println("GIGI = "+gigi);
        System.out.println();
    }
     public static void jumTotalMotor(){
         jumTotalMotor= jumTotalMotor + 1;
    }
/*    public static void main(String[] args) {
        motor s1 = new motor();
        motor.jumTotalMotor();
        s1.kodeMtr="S001";s1.merkMtr="Yamaha R6"; s1.jenisMtr="Road Sport";s1.kecepatan=0;
        s1.gigi=0;
        s1.tambahVelocity(15); s1.kurangVelocity(3);
        s1.tampilData();
        motor S2 = new motor (2);
        motor.jumTotalMotor();
        S2.tampilData();
        motor S3 = new motor (4,25);
        motor.jumTotalMotor();
        S3.tampilData();
        System.out.println("Jumlah Total Obyek Motor yang dibuat "+motor.jumTotalMotor);
    } */
}