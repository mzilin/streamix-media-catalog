package com.mariuszilinskas.vsp.media.catalog;

import com.mariuszilinskas.vsp.media.catalog.config.RabbitMQConfig;
import com.mariuszilinskas.vsp.media.catalog.config.RedisCacheConfig;
import com.mariuszilinskas.vsp.media.catalog.controller.*;
import com.mariuszilinskas.vsp.media.catalog.producer.RabbitMQProducer;
import com.mariuszilinskas.vsp.media.catalog.repository.*;
import com.mariuszilinskas.vsp.media.catalog.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for the Spring application context and bean configuration in the ContentService application.
 */
@SpringBootTest
class CatalogServiceApplicationTests {

	@Autowired
	private MediaServiceImpl mediaService;

	@Autowired
	private MediaAdminServiceImpl mediaAdminService;

	@Autowired
	private SeasonAdminServiceImpl seasonAdminService;

	@Autowired
	private EpisodeAdminServiceImpl episodeAdminService;

	@Autowired
	private TrailerAdminServiceImpl trailerAdminService;

	@Autowired
	private PersonAdminServiceImpl personAdminService;

	@Autowired
	private MediaRepository mediaRepository;

	@Autowired
	private SeasonRepository seasonRepository;

	@Autowired
	private EpisodeRepository episodeRepository;

	@Autowired
	private TrailerRepository trailerRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private MediaController mediaController;

	@Autowired
	private MovieAdminController movieAdminController;

	@Autowired
	private SeriesAdminController seriesAdminController;

	@Autowired
	private SeasonAdminController seasonAdminController;

	@Autowired
	private EpisodeAdminController episodeAdminController;

	@Autowired
	private TrailerAdminController trailerAdminController;

	@Autowired
	private PersonAdminController personAdminController;

	@Autowired
	private RabbitMQConfig rabbitMQConfig;

	@Autowired
	private RedisCacheConfig redisCacheConfig;

	@Autowired
	private RabbitMQProducer rabbitMQProducer;

	@Test
	void contextLoads() {
	}

	@Test
	void mediaServiceBeanLoads() {
		assertNotNull(mediaService, "Media Service should have been auto-wired by Spring Context");
	}

	@Test
	void mediaAdminServiceBeanLoads() {
		assertNotNull(mediaAdminService, "Media Admin Service should have been auto-wired by Spring Context");
	}

	@Test
	void seasonAdminServiceBeanLoads() {
		assertNotNull(seasonAdminService, "Season Admin Service should have been auto-wired by Spring Context");
	}

	@Test
	void episodeAdminServiceBeanLoads() {
		assertNotNull(episodeAdminService, "Episode Admin Service should have been auto-wired by Spring Context");
	}

	@Test
	void trailerAdminServiceBeanLoads() {
		assertNotNull(trailerAdminService, "Trailer Admin Service should have been auto-wired by Spring Context");
	}

	@Test
	void personAdminServiceBeanLoads() {
		assertNotNull(personAdminService, "Person Admin Service should have been auto-wired by Spring Context");
	}

	@Test
	void mediaRepositoryBeanLoads() {
		assertNotNull(mediaRepository, "Media Repository should have been auto-wired by Spring Context");
	}

	@Test
	void seasonRepositoryBeanLoads() {
		assertNotNull(seasonRepository, "Season Repository should have been auto-wired by Spring Context");
	}

	@Test
	void episodeRepositoryBeanLoads() {
		assertNotNull(episodeRepository, "Episode Repository should have been auto-wired by Spring Context");
	}

	@Test
	void trailerRepositoryBeanLoads() {
		assertNotNull(trailerRepository, "Trailer Repository should have been auto-wired by Spring Context");
	}

	@Test
	void personRepositoryBeanLoads() {
		assertNotNull(personRepository, "Person Repository should have been auto-wired by Spring Context");
	}

	@Test
	void mediaControllerBeanLoads() {
		assertNotNull(mediaController, "Media Controller should have been auto-wired by Spring Context");
	}

	@Test
	void movieAdminControllerBeanLoads() {
		assertNotNull(movieAdminController, "Movie Admin Controller should have been auto-wired by Spring Context");
	}

	@Test
	void seriesAdminControllerBeanLoads() {
		assertNotNull(seriesAdminController, "Series Admin Controller should have been auto-wired by Spring Context");
	}

	@Test
	void seasonAdminControllerBeanLoads() {
		assertNotNull(seasonAdminController, "Season Admin Controller should have been auto-wired by Spring Context");
	}

	@Test
	void episodeAdminControllerBeanLoads() {
		assertNotNull(episodeAdminController, "Episode Admin Controller should have been auto-wired by Spring Context");
	}

	@Test
	void trailerAdminControllerBeanLoads() {
		assertNotNull(trailerAdminController, "Trailer Admin Controller should have been auto-wired by Spring Context");
	}

	@Test
	void personAdminControllerBeanLoads() {
		assertNotNull(personAdminController, "Person Admin Controller should have been auto-wired by Spring Context");
	}

	@Test
	void rabbitMQConfigBeanLoads() {
		assertNotNull(rabbitMQConfig, "RabbitMQ Config should have been auto-wired by Spring Context");
	}

	@Test
	void redisCacheConfigBeanLoads() {
		assertNotNull(redisCacheConfig, "Redis Cache Config should have been auto-wired by Spring Context");
	}

	@Test
	void rabbitMQProducerBeanLoads() {
		assertNotNull(rabbitMQProducer, "RabbitMQ Producer should have been auto-wired by Spring Context");
	}

}
