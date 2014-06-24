package Interfaces;

/**
 * @author baez
 */

public interface IUtil {
    /**
     * Extrahiert den Straßennamen aus StreetAddress
     *
     * @param streetAddress StreetAddress aus welcher der Stra0enname extrahiert werden soll
     * @return Straßennamen als String
     */
    public static String extractStreet(String streetAddress) {
        if (streetAddress.length() < 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < streetAddress.length(); i++) {
            if (!Character.isDigit(streetAddress.charAt(i))) {
                sb.append(streetAddress.charAt(i));
            } else
                break;
        }

        return sb.toString().trim();
    }

    /**
     * validiert eine Email-Adresse
     *
     * @param addressToValidate Email-Adresse welche validiert werden soll
     * @return boolsches Ergebnis der Validierung
     */
    public static boolean validateMailAddress(String addressToValidate) {
        boolean atFound = false;
        boolean atCorrect = false;
        boolean pointFound = false;
        boolean pointCorrect = false;
        /*
        validiert die korrekte Position von @
         */
        for (char c : addressToValidate.toCharArray()) {
            if (c == '@' && !atFound) {
                atFound = true;
            } else if (c == '@' && atFound) {
                return false;
            }
        }
        atCorrect = true;
        /*
        validiert die korrekte Position des . nach dem @
         */
        int atIndex = addressToValidate.indexOf("@");
        for (int i = atIndex; i < addressToValidate.length(); i++) {
            if (addressToValidate.charAt(i) == '.' && !pointFound) {
                pointFound = true;
            } else if (addressToValidate.charAt(i) == '.' && pointFound) {
                return false;
            }
        }
        pointCorrect = true;

        return atCorrect & pointCorrect;
    }
}
