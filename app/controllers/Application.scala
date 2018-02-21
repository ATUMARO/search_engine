package controllers

import javax.inject.Inject

import play.Configuration
import play.api.libs.json._
import play.api.libs.ws._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration.Duration

case class Menu(title: String, contensId: String, levelId: String)

class Application @Inject()(ws: WSClient)(config: Configuration) extends Controller {

  val elasticsearchUrl = config.getString("elasticsearch.url")

  def index = Action { implicit request =>

    val res = ws.url(elasticsearchUrl + "/wiki/doc/_search").get().map {
      response =>
        val r = (response.json \ "hits" \ "hits").get.asInstanceOf[JsArray]
        r.value.map(m => {
          val aaa = (m \ "_source" \ "title").get.as[String]
          val bbb = (m \ "_source" \ "content").get.as[String]
          Menu(aaa, aaa, bbb)
        })
    }

    val json = Await.result(res, Duration.Inf)
    Ok(views.html.index(json))

  }

  def test = Action { implicit request =>

    Ok("ああああ")

  }

}
