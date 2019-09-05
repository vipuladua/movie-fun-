package org.superbiz.moviefun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.superbiz.moviefun.albums.Album;
import org.superbiz.moviefun.albums.AlbumFixtures;
import org.superbiz.moviefun.albums.AlbumsBean;
import org.superbiz.moviefun.movies.Movie;
import org.superbiz.moviefun.movies.MovieFixtures;
import org.superbiz.moviefun.movies.MoviesBean;

import java.util.Map;

@Controller
public class HomeController {

    private final MoviesBean moviesBean;
    private final AlbumsBean albumsBean;
    private final MovieFixtures movieFixtures;
    private final AlbumFixtures albumFixtures;
    private final PlatformTransactionManager getPlatformTransactionManagerforMovies ;
    private final PlatformTransactionManager getPlatformTransactionManagerforAlbums;


    public HomeController(MoviesBean moviesBean, AlbumsBean albumsBean, MovieFixtures movieFixtures,
                          AlbumFixtures albumFixtures,
                          PlatformTransactionManager getPlatformTransactionManagerforMovies,
                          PlatformTransactionManager getPlatformTransactionManagerforAlbums
                          ) {
        this.moviesBean = moviesBean;
        this.albumsBean = albumsBean;
        this.movieFixtures = movieFixtures;
        this.albumFixtures = albumFixtures;
        this.getPlatformTransactionManagerforMovies = getPlatformTransactionManagerforMovies;
        this.getPlatformTransactionManagerforAlbums=getPlatformTransactionManagerforAlbums;

    }

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
        createAlbums(model);
        createMovies(model);

        return "setup";

    }




    public void createMovies(Map<String, Object> model) {

        new TransactionTemplate(getPlatformTransactionManagerforMovies).execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                for (Movie movie : movieFixtures.load()) {
                    moviesBean.addMovie(movie);
                }
                model.put("movies", moviesBean.getMovies());
                return null;
            }
        });
    }

    public void createAlbums(Map<String, Object> model) {

        new TransactionTemplate(getPlatformTransactionManagerforAlbums).execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                for (Album album : albumFixtures.load()) {
                    albumsBean.addAlbum(album);
                }
                model.put("albums", albumsBean.getAlbums());
                return null;
            }
        });
    }




}
