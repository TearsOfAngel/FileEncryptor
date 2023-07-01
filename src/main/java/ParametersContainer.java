import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;

public class ParametersContainer {
    private static ZipParameters parameters;
    public static ZipParameters getParameters() {
        if (parameters == null) {
            parameters = new ZipParameters();
            parameters
                    .setCompressionMethod(CompressionMethod.DEFLATE);
            parameters
                    .setCompressionLevel(CompressionLevel.ULTRA);
            parameters
                    .setEncryptFiles(true);
            parameters
                    .setEncryptionMethod(EncryptionMethod.AES);
            parameters
                    .setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
        }
        return parameters;
    }
}
