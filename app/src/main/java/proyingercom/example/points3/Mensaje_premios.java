package proyingercom.example.points3;

import android.widget.TextView;

public class Mensaje_premios {

    String imageUrl, premiosED, puntosED;

    public Mensaje_premios() {
    }

    public Mensaje_premios(String imageUrl, String premiosED, String puntosED) {
        this.imageUrl = imageUrl;
        this.premiosED = premiosED;
        this.puntosED = puntosED;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPremiosED() {
        return premiosED;
    }

    public void setPremiosED(String premiosED) {
        this.premiosED = premiosED;
    }

    public String getPuntosED() {
        return puntosED;
    }

    public void setPuntosED(String puntosED) {
        this.puntosED = puntosED;
    }




}


