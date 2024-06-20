package acme;
class GifEncoderHashitem {
    public int rgb;

    public int count;

    public int index;

    public boolean isTransparent;

    public GifEncoderHashitem(int rgb, int count, int index, boolean isTransparent) {
        this.rgb = rgb;
        this.count = count;
        this.index = index;
        this.isTransparent = isTransparent;
    }
}