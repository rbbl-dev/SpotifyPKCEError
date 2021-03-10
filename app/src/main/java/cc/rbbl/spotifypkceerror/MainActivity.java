package cc.rbbl.spotifypkceerror;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.adamratzman.spotify.SpotifyApiOptions;
import com.adamratzman.spotify.SpotifyClientApi;
import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore;
import com.adamratzman.spotify.javainterop.SpotifyContinuation;
import com.adamratzman.spotify.models.Album;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    public final static int SPOTIFY_SIGN_IN = 1;

    SpotifyDefaultCredentialStore spotifyDefaultCredentialStore;
    SpotifyClientApi mSpotifyClientApi;

    TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainText = findViewById(R.id.album_text);
        spotifyDefaultCredentialStore = SpotifyCredentialStoreHolder.getCredentialStoreInstance(getApplicationContext());
        if (!isSpotifyEnabled()) {
            startActivityForResult(new Intent(this, SpotifyLoginActivity.class), SPOTIFY_SIGN_IN);
        } else {
            initializeSpotifyApi();
        }
    }


    public boolean isSpotifyEnabled() {
        return spotifyDefaultCredentialStore.getSpotifyAccessToken() != null
                && !spotifyDefaultCredentialStore.getSpotifyAccessToken().equals("");
    }

    public void onClickRetry(View view) {
        initializeSpotifyApi();
    }

    public void initializeSpotifyApi() {
        //bypass (working)
        //mSpotifyClientApi = new SpotifyClientApi("myClientId", "", "alarmforspotify://spotify-pkce", spotifyDefaultCredentialStore.getSpotifyToken(), true, true, new SpotifyApiOptions());
        //fetchSpotifyData("spotify:album:1r7EkkbXz7pHt6zLaLMemY");

        //recommended (not working)
        spotifyDefaultCredentialStore.getSpotifyClientPkceApi(null, new SpotifyContinuation<SpotifyClientApi>() {
            @Override
            public void onSuccess(SpotifyClientApi spotifyClientApi) {
                mSpotifyClientApi = spotifyClientApi;
                fetchSpotifyData("spotify:album:1r7EkkbXz7pHt6zLaLMemY");
            }

            @Override
            public void onFailure(@NotNull Throwable throwable) {

            }
        });
    }

    public void fetchSpotifyData(String uri) {
        Context context = getApplication().getApplicationContext();
        String[] uriParts = uri.split(":");
        //TODO differentiate by type
        mSpotifyClientApi.getAlbums().getAlbum(uriParts[2], null, new SpotifyContinuation<Album>() {
            @Override
            public void onSuccess(Album album) {
                mainText.setText(album.getName());
            }

            @Override
            public void onFailure(@NotNull Throwable throwable) {
                Toast.makeText(context, "FUCK! AGAIN!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPOTIFY_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), spotifyDefaultCredentialStore.getSpotifyAccessToken(), Toast.LENGTH_LONG).show();
                initializeSpotifyApi();
                //TODO enable spotify functionality
            }else {
                //TODO disable spotify functionality
            }
        }
    }
}