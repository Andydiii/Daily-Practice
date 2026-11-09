using System;
using System.Drawing;
using System.IO;

// every vertical line must cross the middle row, 
internal class Program
{
    // JPG compression can make black pixels slightly gray.
    private const int BlackThreshold = 128;

    private static void Main(string[] args)
    {
        try
        {
            // requirement: Take exactly one argument	Checks args.Length != 1
            if (args.Length != 1)
            {
                // display error message for an invalid argument count
                Console.WriteLine(
                    "Error: Expected exactly one argument: an absolute image path.");
                Console.WriteLine(
                    "Usage: LineCounter.exe \"C:\\images\\img_1.jpg\"");
                return;
            }

            string imagePath = args[0];

            // Accept an absolute image path only
            if (!Path.IsPathFullyQualified(imagePath))
            {
                throw new ArgumentException(
                    "Please provide an absolute image path.");
            }

            // file does not exsit
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

// based on the question requirement, each line across the middle line, so the middle row contains enough information. so I just traverse the middle row to count the verticle line. A black pixel only increase the count if the preceding pixel is white.

// allow near-black pixels. the threhold 128 accomodates small color changes from jpg compression
// the scan takes O(width) and O(1) in space. excluding image loading and storage