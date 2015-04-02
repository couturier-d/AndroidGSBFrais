package modele;

public class Fiche {
	// {\"mois\":\"201310\",\"idEtat\":\"CR\",\"dateModif\":\"2013-10-08\",\"nbJustificatifs\":\"5\",\"montantValide\":\"0.00\",\"libEtat\":\"Fiche créée, saisie en cours\"}
	private String mois;
	private String idEtat;
	private String dateModif;
	private String nbJustificatifs;
	private String montantValide;
	private String libEtat;
	
	
	
	public String getMois() {
		return mois;
	}
	public void setMois(String mois) {
		this.mois = mois;
	}
	
	public String getIdEtat() {
		return idEtat;
	}
	public void setIdEtat(String idEtat) {
		this.idEtat = idEtat;
	}
	
	public String getDateModif() {
		return dateModif;
	}
	public void setDateModif(String dateModif) {
		this.dateModif = dateModif;
	}
	
	public String getNbJustificatifs() {
		return nbJustificatifs;
	}
	public void setNbJustificatifs(String nbJustificatifs) {
		this.nbJustificatifs = nbJustificatifs;
	}
	
	public String getMontantValide() {
		return montantValide;
	}
	public void setMontantValide(String montantValide) {
		this.montantValide = montantValide;
	}
	
	public String getLibEtat() {
		return libEtat;
	}
	public void setLibEtat(String libEtat) {
		this.libEtat = libEtat;
	}
	
	public int getNumMois(){
		return Integer.parseInt(mois.substring(4));
	}
	
	public String getAnnee() {
		return mois.substring(0,4);
	}
	
	public String convertDate(String uneDate) {
		String[] partiesDate = uneDate.split("-");
		String dateFrancaise = partiesDate[2] + "/" + partiesDate[1]+"/" + partiesDate[0];
		return dateFrancaise;
	}
	
	
}
