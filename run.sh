sudo docker run -p 8081:8081 -p 50000:50000 -d -v jenkins_home:/var/jenkins_home -e JENKINS_OPTS="--httpPort=8081" jenkins/jenkins:lts
