using System;
using System.Drawing;
using System.IO;

internal class Program
{
    // JPG compression can make black pixels slightly gray.
    private const int BlackThreshold = 128;

    private static void Main(string[] args)
    {
        try
        {
            if (args.Length != 1)
            {
                Console.WriteLine(
                    "Error: Expected exactly one argument: an absolute image path.");
                Console.WriteLine(
                    "Usage: LineCounter.exe \"C:\\images\\img_1.jpg\"");
                return;
            }

            string imagePath = args[0];

            if (!Path.IsPathFullyQualified(imagePath))
            {
                throw new ArgumentException(
                    "Please provide an absolute image path.");
            }

            if (!File.Exists(imagePath))
            {
                throw new FileNotFoundException(
                    "Image file does not exist or cannot be accessed.",
                    imagePath);
            }

            // Dispose the image and release its file handle when finished.
            using Bitmap image = new Bitmap(imagePath);

            Console.WriteLine(CountVerticalLines(image));
        }
        catch (Exception exception)
        {
            Console.WriteLine($"Error: {exception}");
        }
    }

    private static int CountVerticalLines(Bitmap image)
    {
        int middleRow = image.Height / 2;
        int lineCount = 0;
        bool previousPixelWasBlack = false;

        for (int x = 0; x < image.Width; x++)
        {
            Color pixel = image.GetPixel(x, middleRow);

            int brightness =
                (pixel.R + pixel.G + pixel.B) / 3;

            bool currentPixelIsBlack = brightness < BlackThreshold;

            // Multiple adjacent black pixels belong to the same line.
            if (currentPixelIsBlack && !previousPixelWasBlack)
            {
                lineCount++;
            }

            previousPixelWasBlack = currentPixelIsBlack;
        }

        return lineCount;
    }
}