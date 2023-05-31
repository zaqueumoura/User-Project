FROM gcr.io/distroless/java17-debian11:nonroot

ENV TZ=America/Recife

WORKDIR /opt/api-user

EXPOSE 8181

COPY --chown=nonroot:nonroot ./target/user-1.0.0.jar /opt/api-user/userservice.jar

CMD ["/opt/api-user/userservice.jar", "-Xms128m", "-Xmx256m"]
