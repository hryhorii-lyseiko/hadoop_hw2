RUN
 hadoop jar /root/hw2/hw2.jar AppDriver hw2in seqoutput seq

READ RESULT
 hadoop fs -libjars /root/hw2/hw2.jar -text seqoutput/part-r-00000 > /root/hw2/output.csv
 cat /root/hw2/output.csv
