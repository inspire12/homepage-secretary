set -xe
# maven wrapper을 이용하여 빌드합니다.
./mvnw clean package

# pom.xml에 정의된 <artifactId>를 읽습니다.
artifactId=`./mvnw -q -s pom.xml \
-Dexec.executable=echo \
-Dexec.args='${project.artifactId}' \
--non-recursive \
exec:exec`

# pom.xml에 정의된 <version>을 읽습니다.
version=`./mvnw -q -s pom.xml \
-Dexec.executable=echo \
-Dexec.args='${project.version}' \
--non-recursive \
exec:exec`

image=${artifactId}/${artifactId}

docker build \
--network host \
--build-arg JAR_FILE="target/${artifactId}-${version}.jar" \
-t ${image}:${version} \
--no-cache .

echo "image=${image}" >> publish.properties
echo "version=${version}" >> publish.properties

