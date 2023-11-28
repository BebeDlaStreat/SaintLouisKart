package fr.bebedlastreat.slkart.tools;

import lombok.experimental.UtilityClass;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

@UtilityClass
public class ImageUtils {

    public static BufferedImage rotate(BufferedImage image, double angle) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();

        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, width / 2, height / 2);
        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        // Création d'une nouvelle image avec les dimensions spécifiées
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();

        // Appliquer la transformation pour redimensionner l'image
        double scaleX = (double) newWidth / originalImage.getWidth();
        double scaleY = (double) newHeight / originalImage.getHeight();
        AffineTransform transform = AffineTransform.getScaleInstance(scaleX, scaleY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, transform, null);
        g2d.dispose();

        return resizedImage;
    }

    public static BufferedImage rotateImageAroundPoint(BufferedImage originalImage, double angle, int rotatePointX, int rotatePointY) {

        // Create a transformation for rotating around the specified point
        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, rotatePointX, rotatePointY);

        // Create a new image with the same dimensions as the original image
        BufferedImage rotatedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        // Apply the transformation and draw the rotated image
        g2d.setTransform(transform);
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

    public static BufferedImage copyImage(BufferedImage source){
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }
}
