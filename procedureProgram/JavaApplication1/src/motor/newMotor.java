
package motor;

public class newMotor {
    
    public static void main(String[] args) {
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
    }
}
