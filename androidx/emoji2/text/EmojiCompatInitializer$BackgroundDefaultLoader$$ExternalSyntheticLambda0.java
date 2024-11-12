package androidx.emoji2.text;

import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.EmojiCompatInitializer;
import java.util.concurrent.ThreadPoolExecutor;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class EmojiCompatInitializer$BackgroundDefaultLoader$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ EmojiCompat.MetadataRepoLoaderCallback f$1;
    public final /* synthetic */ ThreadPoolExecutor f$2;

    public /* synthetic */ EmojiCompatInitializer$BackgroundDefaultLoader$$ExternalSyntheticLambda0(EmojiCompat.MetadataRepoLoaderCallback metadataRepoLoaderCallback, ThreadPoolExecutor threadPoolExecutor) {
        metadataRepoLoaderCallback = metadataRepoLoaderCallback;
        createBackgroundPriorityExecutor = threadPoolExecutor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        EmojiCompatInitializer.BackgroundDefaultLoader.this.m42x5cc8028a(metadataRepoLoaderCallback, createBackgroundPriorityExecutor);
    }
}