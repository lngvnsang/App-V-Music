package com.example.appvmusic.Service;

import com.example.appvmusic.Model.Album;
import com.example.appvmusic.Model.BaiHat;
import com.example.appvmusic.Model.ChuDe;
import com.example.appvmusic.Model.ChuDeTheLoai;
import com.example.appvmusic.Model.Playlist;
import com.example.appvmusic.Model.QuangCao;
import com.example.appvmusic.Model.TheLoai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("song_banner.php")
    Call<List<QuangCao>> GetDataBanner();
    @GET("playlistForCurrentDay.php")
    Call<List<Playlist>> GetPlaylistCurrentDay();
    @GET("chude_theloai_trongngay.php")
    Call<ChuDeTheLoai> GetChuDeTheLoaiToday();
    @GET("albumhot.php")
    Call<List<Album>> GetAlbumToday();
    @GET("baihatduocthich.php")
    Call<List<BaiHat>> GetBaiHatHot();
    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhSachBaiHatTheoQuangCao(@Field("idquangcao") String idquangcao);

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhSachBaiHatTheoPlaylist(@Field("idplaylist") String idplaylist);

    @GET("danhsachcacplaylist.php")
    Call<List<Playlist>> GetDanhSachCacPlaylist();
    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhSachBaiHatTheoTheLoai(@Field("idtheloai") String idtheloai);

    @GET("tatcachude.php")
    Call<List<ChuDe>> GetDanhSachCacChuDe();
    @FormUrlEncoded
    @POST("theloaitheochude.php")
    Call<List<TheLoai>> GetTheLoaiTheoChuDe(@Field("idchude") String idchude);

    @GET("danhsachalbum.php")
    Call<List<Album>> GetDanhSachTatCaAlbum();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
   Call<List<BaiHat>> GetDanhSachBaiHatAlbum(@Field("idalbum") String idalbum);

    @FormUrlEncoded
    @POST("updateluotthich.php")
    Call<String> UpdateLuotThich(@Field("luotthich") String luotthich,@Field("idbaihat") String idbaihat);

    @FormUrlEncoded
    @POST("searchbaihat.php")
    Call<List<BaiHat>> GetSearchBaiHat(@Field("tukhoa") String tukhoa);
}
