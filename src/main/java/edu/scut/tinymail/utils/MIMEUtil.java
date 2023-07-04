package edu.scut.tinymail.utils;

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MIMEUtil {

    public static void main(String[] args) throws Exception {
        String subject = "\n" +
                "=E6=AC=A2=E8=BF=8E=E6=8A=A5=E8=80=83=E6=B2=B3=E6=BA=90=E4=B8=AD=E5=AD=A6 \n" +
                "=E6=82=A8=E5=A5=BD=EF=BC=81\n" +
                "Steam =E5=A4=8F=E6=97=A5=E7=89=B9=E5=8D=96=E7=8E=B0=E5=B7=B2=E7=9B=9B\n" +
                "=E5=A4=A7=E5=BC=80=E5=B9=95\n" +
                "\n" +
                "<https://store.steampowered.com/>\n" +
                "\n" +
                "\n" +
                "\n" +
                "=E5=8F=8C=E4=BA=BA=E6=88=90=E8=A1=8C - =E7=9C=81 60%=EF=BC=81\n" +
                "<https://store.steampowered.com/app/1426210/_/>\n" +
                "\n" +
                "\n" +
                "Sekiro=E2=84=A2: Shadows Die Twice - GOTY Edition - =E7=9C=81 50% =EF=BC=81\n" +
                "<https://store.steampowered.com/app/814380/Sekiro_Shadows_Die_Twice__GOTY_E" +
                "dition/>\n" +
                "\n" +
                "\n" +
                "DARK SOULS=E2=84=A2 III - =E7=9C=81 50%=EF=BC=81\n" +
                "<https://store.steampowered.com/app/374320/DARK_SOULS_III/>\n" +
                "\n" +
                "\n" +
                "=E5=BA=95=E7=89=B9=E5=BE=8B=EF=BC=9A=E5=8C=96=E8=BA=AB=E4=B8=BA=E4=BA=BA - \n" +
                "=E7=9C=81 50%=EF=BC=81\n" +
                "<https://store.steampowered.com/app/1222140/_/>\n" +
                "\n" +
                "\n" +
                "Monster Hunter: World - =E7=9C=81 50%=EF=BC=81\n" +
                "<https://store.steampowered.com/app/582010/Monster_Hunter_World/>\n" +
                "\n" +
                "\n" +
                "RimWorld - =E7=9C=81 20%=EF=BC=81\n" +
                "<https://store.steampowered.com/app/294100/RimWorld/>\n" +
                "\n" +
                "\n" +
                "=E4=BA=BA=E7=B1=BB=E4=B8=80=E8=B4=A5=E6=B6=82=E5=9C=B0 / Human Fall Flat - \n" +
                "=E7=9C=81 70%=EF=BC=81\n" +
                "<https://store.steampowered.com/app/477160/__Human_Fall_Flat/>\n" +
                "\n" +
                "\n" +
                "No Man's Sky - =E7=9C=81 50%=EF=BC=81\n" +
                "<https://store.steampowered.com/app/275850/No_Mans_Sky/>\n" +
                "\n" +
                "\n" +
                "=E9=9B=A8=E4=B8=AD=E5=86=92=E9=99=A9 2 - =E7=9C=81 50%=EF=BC=81\n" +
                "<https://store.steampowered.com/app/632360/_2/>\n" +
                "\n" +
                "\n" +
                "Stray - =E7=9C=81 25%=EF=BC=81\n" +
                "<https://store.steampowered.com/app/1332010/Stray/>\n" +
                "\n" +
                "\n" +
                "=E3=80=8A=E8=B4=A8=E9=87=8F=E6=95=88=E5=BA=94=E3=80=8B=E4=BC=A0=E5=A5=87\n" +
                "=E7=89=88 - =E7=9C=81 75%=EF=BC=81\n" +
                "<https://store.steampowered.com/app/1328670/_/>\n" +
                "\n" +
                "\n" +
                "\n" +
                "=E6=82=A8=E6=94=B6=E5=88=B0=E6=AD=A4=E9=82=AE=E4=BB=B6=E6=98=AF=E5=9B=A0\n" +
                "=E4=B8=BA=E4=B8=8A=E8=BF=B0=E9=A1=B9=E7=9B=AE=E5=9C=A8=E6=82=A8=E7=9A=84 St" +
                "eam =E6=84=BF=E6=9C=9B=E5=8D=95=E4=B8=AD=E3=80=82<https://store.steampowere" +
                "d.com/wishlist/#discount_any=3D1>\n" +
                "=E5=A6=82=E6=9E=9C=E5=B0=86=E6=9D=A5=E6=82=A8=E4=B8=8D=E6=83=B3=E6=94=B6" +
                "=E5=88=B0=E6=84=BF=E6=9C=9B=E5=8D=95=E9=80=9A=E7=9F=A5=E6=B6=88=E6=81=AF" +
                "=EF=BC=8C=E6=82=A8=E5=8F=AF=E4=BB=A5=E9=80=9A=E8=BF=87=E7=82=B9=E5=87=BB" +
                "=E4=BB=A5=E4=B8=8B=E9=93=BE=E6=8E=A5=E7=BC=96=E8=BE=91=E6=82=A8=E7=9A=84" +
                "=E7=94=B5=E5=AD=90=E9=82=AE=E4=BB=B6=E5=81=8F=E5=A5=BD=EF=BC=9Ahttps://stor" +
                "e.steampowered.com/account/emailoptout?token=3Dabcf553661fabd10e2bac405a6a2" +
                "e606cc5887c89cc8e50235d5b13125b5f5484ec3b4b4fbc57bf193b5020ebc33fd25\n" +
                "\n" +
                "\n" +
                "=C2=A9 Valve Corporation<br>PO Box 1688 Bellevue, WA 98009\n" +
                "=E4=BF=9D=E7=95=99=E6=89=80=E6=9C=89=E6=9D=83=E5=88=A9=E3=80=82=E6=89=80" +
                "=E6=9C=89=E5=95=86=E6=A0=87=E5=9D=87=E4=B8=BA=E5=85=B6=E5=9C=A8=E7=BE=8E" +
                "=E5=9B=BD=E5=8F=8A=E5=85=B6=E4=BB=96=E5=9B=BD=E5=AE=B6/=E5=9C=B0=E5=8C" +
                "=BA=E7=9A=84=E5=90=84=E8=87=AA=E6=8C=81=E6=9C=89=E8=80=85=E6=89=80=E6=9C" +
                "=89=E3=80=82";
        Charset c = Charset.forName("utf-8");
        subject = new String(decodeQuotedPrintable(subject.getBytes()), c);
        System.out.println(subject);
    }


    /**
     * QP（QuotedPrintable）解码
     */
    public static byte[] decodeQuotedPrintable(byte[] bytes) throws Exception {
        if (bytes == null) {
            return null;
        }
        byte ESCAPE_CHAR = '=';
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i];
            if (b == ESCAPE_CHAR) {
                int u = Character.digit((char) bytes[++i], 16);
                int l = Character.digit((char) bytes[++i], 16);
                buffer.write((char) ((u << 4) + l));
            } else {
                buffer.write(b);
            }
        }
        return buffer.toByteArray();
    }


}