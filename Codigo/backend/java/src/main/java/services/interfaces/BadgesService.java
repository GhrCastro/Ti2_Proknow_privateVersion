// package services.interfaces;
// package services;

// import models.Badges;
// import dao.BadgesDao;
// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpClient.Version;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;

// public class BadgesService {

// public void insertBadge(Request request, Response response){
// int id = Integer.parseInt(request.queryParams("id"));
// String name = request.queryParams("name");

// Badges badge = new Badges(id,name);

// BadgesDao.insert(badge);
// }
// }