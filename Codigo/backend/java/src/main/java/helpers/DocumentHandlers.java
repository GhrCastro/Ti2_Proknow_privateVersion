package helpers;

import java.util.regex.Pattern;

public class DocumentHandlers {
	public static boolean isCPF(String cpf) {
		String regex = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$";
		return Pattern.matches(regex, cpf);
	}
	
	public static boolean isCNPJ(String cnpj) {
		String regex = "^(\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2})$";
	    return Pattern.matches(regex, cnpj);
	}
	
	public static boolean isEmail(String email) {
	    String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	    return Pattern.matches(regex, email);
	}
}
