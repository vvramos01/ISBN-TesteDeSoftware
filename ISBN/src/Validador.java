public class Validador {

    public static boolean validarISBN13(String ISBN){
        String isbn = ISBN.replaceAll("-", "");
        int soma = 0;
        for (int i = 0; i < isbn.length(); i++){
            if (i % 2 == 0){
                System.out.println(isbn.charAt(i) + " x " + 1 + " = " + isbn.charAt(i) * 1);
            }else {
                System.out.println(isbn.charAt(i) + " x " + 3 + " = " + isbn.charAt(i) * 3);
            }
        }
        int r1 = soma % 10;
        int dv = (10 - r1) % 10;

        if ISBN.chatAt(12) == dv {
            return true;
        }
//        `S % 10 = 3` ⇒ `DV = (10 − 3) % 10 = 7`
        return false;
    }
}
