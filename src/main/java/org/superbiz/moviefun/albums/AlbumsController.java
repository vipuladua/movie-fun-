package org.superbiz.moviefun.albums;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



import java.util.Map;

@Controller
public class AlbumsController {

    private final AlbumsBean albumsBean;
    private final AlbumFixtures albumFixtures;

    public AlbumsController(AlbumsBean albumsBean, AlbumFixtures albumFixtures) {
        this.albumsBean = albumsBean;
        this.albumFixtures = albumFixtures;
    }


    @GetMapping("/albums")
    public String index(Map<String, Object> model) {
        model.put("albums", albumsBean.getAlbums());
        return "albums";
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
        for (Album album : albumFixtures.load()) {
            albumsBean.addAlbum(album);
        }

        model.put("albums", albumsBean.getAlbums());

        return "setup";
    }
}
