package com.Havhingstor.BetterTeamChat.ArgumentType;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.concurrent.CompletableFuture;

public class PlayerSuggestions implements SuggestionProvider {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String name = builder.getRemaining();

        for(String suggestion: Utils.getSuggestions(name)) {
            builder.suggest(suggestion);
        }

        return builder.buildFuture();
    }
}
