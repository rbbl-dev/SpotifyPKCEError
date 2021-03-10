package cc.rbbl.spotifypkceerror;

import android.content.Context;

import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore;


public class SpotifyCredentialStoreHolder {
    private static SpotifyDefaultCredentialStore instance;

    private SpotifyCredentialStoreHolder () {}

    public static SpotifyDefaultCredentialStore getCredentialStoreInstance (Context applicationContext) {
        if (SpotifyCredentialStoreHolder.instance == null) {
            SpotifyCredentialStoreHolder.instance = new SpotifyDefaultCredentialStore(
                    "myClientId",
                    //"http://rbbl.cc/test",
                    "alarmforspotify://spotify-pkce",
                    applicationContext);
        }
        return SpotifyCredentialStoreHolder.instance;
    }
}
