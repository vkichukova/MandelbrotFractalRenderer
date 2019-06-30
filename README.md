# MandelbrotFractalRenderer

Usage: java -jar Mandelbrot.jar [options] 

Options:

-s (or -size) Image size: width x height (default "640x480"): 

-r (or -rect) Complex Plane Range (default "-2.0:2.0:-1.0:1.0")

-o (or -output) Name of the output file (default "mandelbrot_fractal.png")

-t (or -tasks) Number of threads (default 1)

-q (or -quiet) Enables quiet mode, i.e. no detailed logging (default false)

E.g. java -jar Mandelbrot.jar -s 2048x1024 -o file.png -t 4 -q
