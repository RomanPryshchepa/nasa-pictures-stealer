package com.bobocode.restApiDemo.repository;

import com.bobocode.restApiDemo.entity.Camera;
import com.bobocode.restApiDemo.entity.Picture;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class NasaRepository {

  @Value("${db.url}")
  private String dbUrl;

  @Value("${db.user}")
  private String dbUser;

  @Value("${db.pass}")
  private String dbPass;

  private int saveCamera(Camera camera, Connection conn) {
    try (PreparedStatement psSave = conn.prepareStatement(
        "INSERT INTO cameras (nasa_id, name) values (?,?)");
        PreparedStatement psGet = conn.prepareStatement(
            "SELECT id FROM cameras WHERE nasa_id = ?")) {
      psSave.setInt(1, camera.getNasaId());
      psSave.setString(2, camera.getName());
      psSave.executeUpdate();

      psGet.setInt(1, camera.getNasaId());
      ResultSet resultSet = psGet.executeQuery();
      resultSet.next();
      return resultSet.getInt("ID");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private int getCamera(Camera camera, Connection conn) {
    try (PreparedStatement psGet = conn.prepareStatement(
        "SELECT id FROM cameras WHERE nasa_id = ?")) {
      psGet.setInt(1, camera.getNasaId());
      ResultSet resultSet = psGet.executeQuery();
      if (resultSet.next()) {
        return resultSet.getInt("ID");
      } else {
        return -1;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void savePicture(Picture picture, Connection conn) {
    try (PreparedStatement ps = conn.prepareStatement(
        "INSERT INTO pictures (nasa_id, img_src, camera_id) values (?,?,?)")) {
      ps.setInt(1, picture.getNasaId());
      ps.setString(2, picture.getImgSrc());
      Camera camera = picture.getCamera();
      int cameraId = getCamera(camera, conn);
      if (cameraId == -1) {
        cameraId = saveCamera(camera, conn);
      }
      ps.setInt(3, cameraId);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void saveAllPictures(List<Picture> pictures) {
    try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
      for (Picture picture : pictures) {
        savePicture(picture, conn);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
