package com.jmonkey.office.lexi.support;
public interface FileActionListener {
    public void editorNew();

    public void editorOpen();

    public void editorOpenAs();

    public void editorRevert(com.jmonkey.office.lexi.support.Editor editor);

    public void editorSave(com.jmonkey.office.lexi.support.Editor editor);

    public void editorSaveAs(com.jmonkey.office.lexi.support.Editor editor);

    public void editorSaveCopy(com.jmonkey.office.lexi.support.Editor editor);
}