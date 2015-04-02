package tec.couturier.anbdroidgsbfrais;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import modele.Fiche;
import modele.FichesVisiteur;
import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	private ListView listeViewFrais;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialisation();
	}
	
	private void initialisation() {
		RequestQueue queue = Volley.newRequestQueue(this);
		final String url = "http://10.29.2.241/~couturier-d/SLAM5/gsb_copie/service/service.php?service=getDbFiches&idVisiteur=a131";
		
		//Préparation de la requête
		JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						Log.d("Response", response.toString());
						remplirListe(response.toString());
						
					}
				},
				new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						Log.d("Error.response", error.toString());
					}
				}
			);
		
		queue.add(getRequest);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void remplirListe(String jSonFlow) {
		listeViewFrais = (ListView) findViewById(R.id.listViewFrais);
				
//		String jSonFlow = "{\"fichesVisiteur\":[{\"mois\":\"201310\",\"idEtat\":\"CR\",\"dateModif\":\"2013-10-08\",\"nbJustificatifs\":\"5\",\"montantValide\":\"0.00\",\"libEtat\":\"Fiche créée, saisie en cours\"},{\"mois\":\"201301\",\"idEtat\":\"CL\",\"dateModif\":\"2013-03-05\",\"nbJustificatifs\":\"5\",\"montantValide\":\"4447.58\",\"libEtat\":\"Saisie cloturée\"},{\"mois\":\"201302\",\"idEtat\":\"VA\",\"dateModif\":\"2013-04-08\",\"nbJustificatifs\":\"4\",\"montantValide\":\"3860.96\",\"libEtat\":\"Validée et mise en paiement\"},{\"mois\":\"201303\",\"idEtat\":\"RB\",\"dateModif\":\"2013-05-04\",\"nbJustificatifs\":\"6\",\"montantValide\":\"3601.34\",\"libEtat\":\"Remboursé\"},{\"mois\":\"201304\",\"idEtat\":\"RB\",\"dateModif\":\"2013-06-05\",\"nbJustificatifs\":\"1\",\"montantValide\":\"3699.08\",\"libEtat\":\"Remboursé\"},{\"mois\":\"201305\",\"idEtat\":\"RB\",\"dateModif\":\"2013-07-06\",\"nbJustificatifs\":\"2\",\"montantValide\":\"3333.82\",\"libEtat\":\"Remboursé\"},{\"mois\":\"201306\",\"idEtat\":\"RB\",\"dateModif\":\"2013-08-05\",\"nbJustificatifs\":\"6\",\"montantValide\":\"3073.72\",\"libEtat\":\"Remboursé\"},{\"mois\":\"201307\",\"idEtat\":\"RB\",\"dateModif\":\"2013-09-02\",\"nbJustificatifs\":\"2\",\"montantValide\":\"2348.84\",\"libEtat\":\"Remboursé\"},{\"mois\":\"201308\",\"idEtat\":\"RB\",\"dateModif\":\"2013-10-02\",\"nbJustificatifs\":\"1\",\"montantValide\":\"2525.48\",\"libEtat\":\"Remboursé\"},{\"mois\":\"201309\",\"idEtat\":\"RB\",\"dateModif\":\"2013-11-27\",\"nbJustificatifs\":\"2\",\"montantValide\":\"3676.90\",\"libEtat\":\"Remboursé\"}]}";
		
		FichesVisiteur fiches = new Gson().fromJson(jSonFlow, FichesVisiteur.class);
		System.out.println("nb fiches instanciées : "+fiches.getFichesVisiteur().size());
		
		String[] mois = new DateFormatSymbols(Locale.FRENCH).getMonths();
//		System.out.println(mois);
//		String[] etats = {"","cr", "cl", "cl","cl","va","va","va","rb","rb","rb"};
		
		//Génération des lignes de fiches de frais
		ArrayList<HashMap<String, String>> listeFiches = new ArrayList<HashMap<String, String>>();
		for(Fiche laFiche : fiches.getFichesVisiteur()) {
			HashMap<String, String> itemListe = new HashMap<String, String>();
			itemListe.put("mois", mois[laFiche.getNumMois()-1] + " " + laFiche.getAnnee());
			itemListe.put("etat", laFiche.getLibEtat()+ " le " + laFiche.convertDate(laFiche.getDateModif()));
			itemListe.put("montant", laFiche.getMontantValide() + "€");
			int idRes = this.getResources().getIdentifier(laFiche.getIdEtat().toLowerCase(), "drawable", this.getPackageName());
			itemListe.put("imgetat", Integer.toString(idRes));
			
			listeFiches.add(itemListe);
		}
		
		ListAdapter adapter = new SimpleAdapter(
			this, 
			listeFiches,
			R.layout.contenu_list,
			new String[] {"mois", "etat", "montant", "imgetat"},
			new int[] {R.id.textViewMois, R.id.textViewEtat, R.id.textViewMontant, R.id.imgetat}
		);
		
		listeViewFrais.setAdapter(adapter);
	}
}
