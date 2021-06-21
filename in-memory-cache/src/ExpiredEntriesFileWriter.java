import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExpiredEntriesFileWriter implements ICacheEntriesWriter {
private  String fileName;
private BufferedWriter writer;
public ExpiredEntriesFileWriter(String fileName) {
	this.fileName=fileName;
	try {
		writer = new BufferedWriter(new FileWriter(this.fileName,true));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@Override
public boolean writeEntries(CacheObject obj) {
	// TODO Auto-generated method stub
	
	try {
		
		this.writer.append("["+obj.toString()+"]\r\n");
		this.writer.flush();
	} catch (IOException e) {
		System.out.println("Error Occured while writing into File :"+ e.getMessage());
		e.printStackTrace();
		
	} 
	
	return false;
}



}
