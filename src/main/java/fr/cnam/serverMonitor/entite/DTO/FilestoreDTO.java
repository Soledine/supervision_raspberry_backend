package fr.cnam.serverMonitor.entite.DTO;

import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.List;

public record FilestoreDTO(long totalSpace, long freeSpace, String name) {
    public FilestoreDTO(FileStore fileStore) throws IOException{
        this(fileStore.getTotalSpace(), fileStore.getUsableSpace(), fileStore.toString());
    }
    public static List<FilestoreDTO> getFileStoreListDTO(Iterable <FileStore> fileStoreList){
        List<FilestoreDTO> liste= new ArrayList<>();
            fileStoreList.iterator().forEachRemaining(fs -> {
                try {
                    liste.add(new FilestoreDTO(fs));
                }
                catch(IOException e){
                    System.out.println("acces impossible sur le filestore "+fs.name());
                }
            });

        return liste;
    }
}
