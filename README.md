

# VIVO: Connect, Share, Discover

[![Build](https://github.com/vivo-project/VIVO/workflows/Build/badge.svg)](https://github.com/vivo-project/VIVO/actions?query=workflow%3ABuild) [![Deploy](https://github.com/vivo-project/VIVO/workflows/Deploy/badge.svg)](https://github.com/vivo-project/VIVO/actions?query=workflow%3ADeploy) [![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.2639714.svg)](https://doi.org/10.5281/zenodo.2639713)

VIVO is an open source semantic web tool for research discovery -- finding people and the research they do.

VIVO supports editing, searching, browsing and visualizing research activity in order to discover people, programs, 
facilities, funding, scholarly works and events. VIVO's search returns results faceted by type for rapid retrieval of 
desired information across disciplines.

## Resources

### VIVO Project web site
http://vivoweb.org/

### VIVO Project Wiki
https://wiki.lyrasis.org/display/VIVO/

### Installation Instructions

Installation instructions for all releases can be found at this location on the wiki:  
https://wiki.lyrasis.org/display/VIVODOC/All+Documentation

When you select the wiki pages for technical documentation for the release you would like to install at https://wiki.lyrasis.org/display/VIVODOC/All+Documentation, please open the Installing VIVO section and follow the instructions. 

## Docker run

VIVO docker container is available at [vivoweb/vivo](https://hub.docker.com/repository/docker/vivoweb/vivo) with accompanying [vivoweb/vivo-solr](https://hub.docker.com/repository/docker/vivoweb/vivo-solr). These can be used independently or with docker-compose.

###  VIVO Build
Before building VIVO, you will also need to clone (and switch to the same branch, if other than main) of [Vitro](https://github.com/vivo-project/Vitro). The Vitro project must be cloned to a sibling directory next to VIVO so that it can be found during the build. 
```
cd $WORKSPACE
git clone https://github.com/vivo-project/VIVO.git
git clone https://github.com/vivo-project/Vitro.git
cd $WORKSPACE/VIVO
#    edit home/src/main/resources/config/example.runtime.properties
#    in line 93
#    replace http://localhost:8983/solr/vivocore 
#    by http://solr:8983/solr/vivocore 
vi home/src/main/resources/config/example.runtime.properties 
mvn clean package -s installer/example-settings.xml
```

### Docker Compose

Docker Compose environment variables:

.env defaults
```
LOCAL_VIVO_HOME=./vivo-home
RESET_HOME=false
RESET_CORE=false
```

- `LOCAL_VIVO_HOME`: VIVO home directory on your host machine which will mount to volume in docker container. Set this environment variable to persist your VIVO data on your host machine.
- `RESET_HOME`: Convenience to reset VIVO home when starting container. **Caution**, will delete local configuration, content, and configuration model.
- `RESET_CORE`: Convenience to reset VIVO Solr core when starting container. **Caution**, will require complete reindex.

#### Start VIVO.

1. In VIVO (with Vitro cloned alongside it), run:
```
docker-compose build
docker-compose up
```

### Docker Image

To build and run local Docker image.

```
# Create a docker connection network
docker network create vivo-net
# Run solr docker
docker run --net vivo-net --name solr -p 8983:8983 -d vivoweb/vivo-solr:latest
# Start build docker for VIVO
docker build -t vivoweb/vivo:development .
# Run VIVO docker
docker run --net vivo-net --name vivo -p 8080:8080 -d vivoweb/vivo:development
# Logging VIVO
docker logs --follow /vivo
```
### Useful docker commands
#### List containers
```
docker ps -a
```
#### List images
```
docker images -a
```
#### Stop/Start VIVO/SOLR
```
docker stop /vivo
docker stop /solr
docker start /vivo
docker start /solr
```
#### Removing the containers
```
docker rm /vivo
docker rm /solr
```

## Contact us
There are several ways to contact the VIVO community. 
Whatever your interest, we would be pleased to hear from you.

### Contact form 
https://vivo.lyrasis.org/contact/

### Mailing lists

#### [vivo-all](https://groups.google.com/forum/#!forum/vivo-all) 
This updates list provides news to the VIVO community of interest to all.

#### [vivo-community](https://groups.google.com/forum/#!forum/vivo-community)  
Join the VIVO community!  Here you'll find non-technical discussion regarding participation, the VIVO
conference,  policy, project management, outreach, and engagement. 

#### [vivo-tech](https://groups.google.com/forum/#!forum/vivo-tech)  
The best place to get your hands dirty in the VIVO Project. 
Developers and implementers frequent this list to get the latest on feature design, 
development, implementation, and testing.

## Contributing Code
If you would like to contribute code to the VIVO project, please open a ticket 
in our [JIRA](https://jira.lyrasis.org/projects/VIVO), and prepare a 
pull request that references your ticket.  Contributors welcome!

## Citing VIVO
If you are using VIVO in your publications or projects, please cite the software paper in the Journal of Open Source Software:

* Conlon et al., (2019). VIVO: a system for research discovery. Journal of Open Source Software, 4(39), 1182, https://doi.org/10.21105/joss.01182

### BibTeX
```tex
@article{Conlon2019,
  doi = {10.21105/joss.01182},
  url = {https://doi.org/10.21105/joss.01182},
  year = {2019},
  publisher = {The Open Journal},
  volume = {4},
  number = {39},
  pages = {1182},
  author = {Michael Conlon and Andrew Woods and Graham Triggs and Ralph O'Flinn and Muhammad Javed and Jim Blake and Benjamin Gross and Qazi Asim Ijaz Ahmad and Sabih Ali and Martin Barber and Don Elsborg and Kitio Fofack and Christian Hauschke and Violeta Ilik and Huda Khan and Ted Lawless and Jacob Levernier and Brian Lowe and Jose Martin and Steve McKay and Simon Porter and Tatiana Walther and Marijane White and Stefan Wolff and Rebecca Younes},
  title = {{VIVO}: a system for research discovery},
  journal = {Journal of Open Source Software}
}
