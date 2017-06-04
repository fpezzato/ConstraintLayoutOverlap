# ConstraintLayoutOverlap

# TL;DR
If you are using the (awesome!) ConstrainLayout* on Android, there is no out-of-the-box way to hide a view if the screen is too small to contain everything.
If you have this requirement, keep on reading!

*= version 1.1.0-beta1 at the time of writing

# Requirements
Consider you are required to:
* have a view with a specific width to height ratio
* have such view "full bleed" 
* hide such view if there is no space left on small screen


## Case 1 
If you are using a framework layout (eg. ConstraintLayout, FrameLayout, etc.)
and you don't need to change it's size dynamically you can just register an `OnPreDrawListener` and hide the view 

````kotlin
main_container.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                main_container.viewTreeObserver.removeOnPreDrawListener(this)

                if (top_view.bottom > bottom_view.top) {
                    bottom_view.visibility = GONE
                }
                return false //This is important! Otherwise the first frame with the view flashes on screen.
            }
````
The small drawback is: you loose a frame, but fair enough!

## Case 2 
If instead you want to animate the container, you can override `onLayout` and make some calculation.
In this git repo you can find an example called `NotOverlappingConstraintLayout` and in this case the usage is pretty simple:
````kotlin
 main_container.hide(bottom_view).whenOverlapping(top_view)
````
On the plus size: not a single frame is lost! yay!

In the following demo: the 'snake' image cannot be resized, but can be hidden
![Demo](https://thumbs.gfycat.com/DismalGreedyGoral-size_restricted.gif)

# Note:
1. This is a proof of concept, but if there is any interest just let me know I can extract it into a proper library
2. For this demo I'm using a ConstraintLayout. The latest release is 1.1.0-beta1. If in the future this use case will be supported I'll deprecate this entire project!
3. Are you wondering: who wrote such strange UI requirements, where a view cannot be resized but can be hidden? Well, just one word: _advert_! 

---
Thanks to pixabay.com for the images used in the sample code.
