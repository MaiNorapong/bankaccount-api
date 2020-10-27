FROM maven:3.6.3-jdk-8

RUN git clone https://github.com/MaiNorapong/bankaccount-api.git
WORKDIR /bankaccount-api
RUN mvn compile

ADD https://api.github.com/repos/MaiNorapong/bankaccount-api/git/refs/heads/master version.json
RUN git pull
RUN mvn compile

RUN chmod +x wait-for-it.sh
EXPOSE 8091
CMD ["mvn", "exec:java", "-Dexec.mainClass=th.ac.ku.bankaccount.BankaccountApplication"]
