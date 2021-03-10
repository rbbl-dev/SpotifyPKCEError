package cc.rbbl.spotifypkceerror;

import com.adamratzman.spotify.SpotifyClientApi;
import com.adamratzman.spotify.SpotifyScope;
import com.adamratzman.spotify.auth.pkce.AbstractSpotifyPkceLoginActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class SpotifyLoginActivity extends AbstractSpotifyPkceLoginActivity {

    @NotNull
    @Override
    public String getClientId() {
        return "myClientId";
    }

    @NotNull
    @Override
    public String getRedirectUri() {
        return "alarmforspotify://spotify-pkce";
    }

    @NotNull
    @Override
    public List<SpotifyScope> getScopes() {
        return Arrays.asList(SpotifyScope.values());
    }

    @Override
    public void onFailure(@NotNull Exception e) {
        e.printStackTrace();
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onSuccess(@NotNull SpotifyClientApi spotifyClientApi) {
        SpotifyCredentialStoreHolder.getCredentialStoreInstance(getApplicationContext()).setSpotifyApi(spotifyClientApi);
        setResult(RESULT_OK);
        finish();
    }
}
