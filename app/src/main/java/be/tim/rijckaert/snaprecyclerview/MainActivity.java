package be.tim.rijckaert.snaprecyclerview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        final List<Article> articles = createArticles();

        final SnapRecyclerAdapter adapter = new SnapRecyclerAdapter(this, articles);
        recyclerView.setAdapter(adapter);

        //------------- SNAPPING BEHAVIOR -----------
        //Standard Snap Helper searches for the view in the center of the screen and tries to center that view
        //by smooth scrolling to the center position

        //SnapHelper baseSnapHelper = new LinearSnapHelper();
        //baseSnapHelper.attachToRecyclerView(recyclerView); //uncomment for standard 'snaphelper' behavior

        final GravitySnapHelper gravitySnapHelper = new GravitySnapHelper(Gravity.TOP);
        gravitySnapHelper.attachToRecyclerView(recyclerView);
    }

    @NonNull
    private List<Article> createArticles() {
        return Arrays.asList(
                new Article("De Wever: \"Had suïcidale gedachten in puberteit\"\n", "Bart De Wever heeft in zijn puberteit geworsteld met suïcidale gedachten. Dat heeft hij toevertrouwd in \"Touché\" op Radio 1. \"Ik heb het twee jaar heel lastig gehad met mezelf, maar ik ben er vanzelf uitgeraakt.\" De Wever weerlegde ook het beeld dat hij de N-VA als een dictator zou leiden. \"Ik ben een enorme twijfelaar. Als ik een stalinist was, dan had ik het nekschot al een jaar geleden gegeven\", illustreert hij dat aan de hand van de kwestie Vuye/Wouters.", "http://images.vrt.be/landscape960/2016/09/25/b9ed78b4-830d-11e6-aef1-00163edf48dd.jpg"),
                new Article("Twee tienermeisjes uit Nice opgepakt omdat ze aanslag planden\n", "In de Zuid-Franse stad Nice zijn halverwege deze maand twee meisjes opgepakt omdat ze van plan waren een terreuraanslag te plegen. De tieners stonden in contact met een militant van terreurgroep IS. Ze zitten momenteel in voorlopige hechtenis.", "http://images.vrt.be/landscape960/2016/09/25/17c16058-8320-11e6-aef1-00163edf48dd.jpg"),
                new Article("Zieke Chirac kroop door het oog van de naald\n", "Oud-president van Frankrijk Jacques Chirac heeft vorige zondag een tijd tussen leven en dood gehangen. Dat melden ingewijden aan het Franse blad Le Journal du Dimanche. De 83-jarige Chirac ligt al een week in een Parijs ziekenhuis met een zware longontsteking.", "http://images.vrt.be/landscape960/2016/09/25/7f89d08f-831c-11e6-aef1-00163edf48dd.jpg"),
                new Article("Amerikaanse acteur Bill Nunn overleden\n", "In zijn woonplaats in Pittsburg in de Amerikaanse staat Pennsylvania is de acteur Bill Nun overleden. Nunn speelde in een resem van films, waaronder de \"Spider-Man\"-filmreeks en \"Do the right thing\" en \"He got game\" van regisseur Spike Lee. Nunn was 62. Hij leed al enige tijd aan kanker.", "http://images.vrt.be/landscape960/2016/09/25/2c3bfa7e-831c-11e6-aef1-00163edf48dd.jpg"),
                new Article("Schrijver en journalist Nahed Hattar doodgeschoten op straat in Jordanië\n", "In de Jordaanse hoofdstad Amman is de schrijver en journalist Nahed Hattar op straat doodgeschoten. Hij was op weg naar een rechtbank waar hij moest terechtstaan wegens het beledigen van god en islam. Hattar stond bekend om zijn politieke satires. De schutter vuurde driemaal op de schrijver. De dader werd opgepakt.", "http://images.vrt.be/landscape960/2016/09/25/856bd0da-831b-11e6-aef1-00163edf48dd.jpg"),
                new Article("De Wever: \"Had suïcidale gedachten in puberteit\"\n", "Bart De Wever heeft in zijn puberteit geworsteld met suïcidale gedachten. Dat heeft hij toevertrouwd in \"Touché\" op Radio 1. \"Ik heb het twee jaar heel lastig gehad met mezelf, maar ik ben er vanzelf uitgeraakt.\" De Wever weerlegde ook het beeld dat hij de N-VA als een dictator zou leiden. \"Ik ben een enorme twijfelaar. Als ik een stalinist was, dan had ik het nekschot al een jaar geleden gegeven\", illustreert hij dat aan de hand van de kwestie Vuye/Wouters.", "http://images.vrt.be/landscape960/2016/09/25/b9ed78b4-830d-11e6-aef1-00163edf48dd.jpg"),
                new Article("Twee tienermeisjes uit Nice opgepakt omdat ze aanslag planden\n", "In de Zuid-Franse stad Nice zijn halverwege deze maand twee meisjes opgepakt omdat ze van plan waren een terreuraanslag te plegen. De tieners stonden in contact met een militant van terreurgroep IS. Ze zitten momenteel in voorlopige hechtenis.", "http://images.vrt.be/landscape960/2016/09/25/17c16058-8320-11e6-aef1-00163edf48dd.jpg"),
                new Article("Zieke Chirac kroop door het oog van de naald\n", "Oud-president van Frankrijk Jacques Chirac heeft vorige zondag een tijd tussen leven en dood gehangen. Dat melden ingewijden aan het Franse blad Le Journal du Dimanche. De 83-jarige Chirac ligt al een week in een Parijs ziekenhuis met een zware longontsteking.", "http://images.vrt.be/landscape960/2016/09/25/7f89d08f-831c-11e6-aef1-00163edf48dd.jpg"),
                new Article("Amerikaanse acteur Bill Nunn overleden\n", "In zijn woonplaats in Pittsburg in de Amerikaanse staat Pennsylvania is de acteur Bill Nun overleden. Nunn speelde in een resem van films, waaronder de \"Spider-Man\"-filmreeks en \"Do the right thing\" en \"He got game\" van regisseur Spike Lee. Nunn was 62. Hij leed al enige tijd aan kanker.", "http://images.vrt.be/landscape960/2016/09/25/2c3bfa7e-831c-11e6-aef1-00163edf48dd.jpg"),
                new Article("Schrijver en journalist Nahed Hattar doodgeschoten op straat in Jordanië\n", "In de Jordaanse hoofdstad Amman is de schrijver en journalist Nahed Hattar op straat doodgeschoten. Hij was op weg naar een rechtbank waar hij moest terechtstaan wegens het beledigen van god en islam. Hattar stond bekend om zijn politieke satires. De schutter vuurde driemaal op de schrijver. De dader werd opgepakt.", "http://images.vrt.be/landscape960/2016/09/25/856bd0da-831b-11e6-aef1-00163edf48dd.jpg"),
                new Article("De Wever: \"Had suïcidale gedachten in puberteit\"\n", "Bart De Wever heeft in zijn puberteit geworsteld met suïcidale gedachten. Dat heeft hij toevertrouwd in \"Touché\" op Radio 1. \"Ik heb het twee jaar heel lastig gehad met mezelf, maar ik ben er vanzelf uitgeraakt.\" De Wever weerlegde ook het beeld dat hij de N-VA als een dictator zou leiden. \"Ik ben een enorme twijfelaar. Als ik een stalinist was, dan had ik het nekschot al een jaar geleden gegeven\", illustreert hij dat aan de hand van de kwestie Vuye/Wouters.", "http://images.vrt.be/landscape960/2016/09/25/b9ed78b4-830d-11e6-aef1-00163edf48dd.jpg"),
                new Article("Twee tienermeisjes uit Nice opgepakt omdat ze aanslag planden\n", "In de Zuid-Franse stad Nice zijn halverwege deze maand twee meisjes opgepakt omdat ze van plan waren een terreuraanslag te plegen. De tieners stonden in contact met een militant van terreurgroep IS. Ze zitten momenteel in voorlopige hechtenis.", "http://images.vrt.be/landscape960/2016/09/25/17c16058-8320-11e6-aef1-00163edf48dd.jpg"),
                new Article("Zieke Chirac kroop door het oog van de naald\n", "Oud-president van Frankrijk Jacques Chirac heeft vorige zondag een tijd tussen leven en dood gehangen. Dat melden ingewijden aan het Franse blad Le Journal du Dimanche. De 83-jarige Chirac ligt al een week in een Parijs ziekenhuis met een zware longontsteking.", "http://images.vrt.be/landscape960/2016/09/25/7f89d08f-831c-11e6-aef1-00163edf48dd.jpg"),
                new Article("Amerikaanse acteur Bill Nunn overleden\n", "In zijn woonplaats in Pittsburg in de Amerikaanse staat Pennsylvania is de acteur Bill Nun overleden. Nunn speelde in een resem van films, waaronder de \"Spider-Man\"-filmreeks en \"Do the right thing\" en \"He got game\" van regisseur Spike Lee. Nunn was 62. Hij leed al enige tijd aan kanker.", "http://images.vrt.be/landscape960/2016/09/25/2c3bfa7e-831c-11e6-aef1-00163edf48dd.jpg"),
                new Article("Schrijver en journalist Nahed Hattar doodgeschoten op straat in Jordanië\n", "In de Jordaanse hoofdstad Amman is de schrijver en journalist Nahed Hattar op straat doodgeschoten. Hij was op weg naar een rechtbank waar hij moest terechtstaan wegens het beledigen van god en islam. Hattar stond bekend om zijn politieke satires. De schutter vuurde driemaal op de schrijver. De dader werd opgepakt.", "http://images.vrt.be/landscape960/2016/09/25/856bd0da-831b-11e6-aef1-00163edf48dd.jpg")
        );
    }
}
